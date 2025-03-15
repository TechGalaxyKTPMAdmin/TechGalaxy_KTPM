package iuh.fit.se.orderservice.service.impl;

import com.rabbitmq.client.Channel;
import iuh.fit.se.orderservice.client.CustomerClient;
import iuh.fit.se.orderservice.client.InventoryClient;
import iuh.fit.se.orderservice.dto.request.EmailRequest;
import iuh.fit.se.orderservice.dto.request.NotificationDto;
import iuh.fit.se.orderservice.dto.request.OrderCreateRequest;
import iuh.fit.se.orderservice.dto.request.OrderRequest;
import iuh.fit.se.orderservice.dto.request.PaymentMethod;
import iuh.fit.se.orderservice.dto.response.CustomerResponseV2;
import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.dto.response.OrderResponseCache;
import iuh.fit.se.orderservice.dto.response.PaymentStatusResponse;
import iuh.fit.se.orderservice.entity.Order;
import iuh.fit.se.orderservice.entity.OrderDetail;
import iuh.fit.se.orderservice.entity.enumeration.DetailStatus;
import iuh.fit.se.orderservice.entity.enumeration.OrderStatus;
import iuh.fit.se.orderservice.entity.enumeration.PaymentStatus;
import iuh.fit.se.orderservice.event.InventoryUpdateMessage;
import iuh.fit.se.orderservice.event.OrderEvent;
import iuh.fit.se.orderservice.exception.AppException;
import iuh.fit.se.orderservice.exception.ErrorCode;
import iuh.fit.se.orderservice.mapper.OrderMapper;
import iuh.fit.se.orderservice.repository.OrderDetailRepository;
import iuh.fit.se.orderservice.repository.OrderRepository;
import iuh.fit.se.orderservice.service.OrderDetailService;
import iuh.fit.se.orderservice.service.OrderService;
import iuh.fit.se.orderservice.service.RabbitMQSenderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final RabbitMQSenderService rabbitMQSenderService;
    private final InventoryClient inventoryClient;
    private final CustomerClient customerClient;
    private final OrderDetailService orderDetailService;
    private final OrderResponseCache orderResponseCache;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
                            RabbitMQSenderService rabbitMQSenderService, InventoryClient inventoryClient,
                            CustomerClient customerClient, OrderDetailService orderDetailService,
                            OrderResponseCache orderResponseCache, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.rabbitMQSenderService = rabbitMQSenderService;
        this.inventoryClient = inventoryClient;
        this.customerClient = customerClient;
        this.orderDetailService = orderDetailService;
        this.orderResponseCache = orderResponseCache;
        this.rabbitTemplate = rabbitTemplate;
    }
    private final String orderExchange = "order.exchange";
    private final String orderCreatedRoutingKey = "order.created";
    private final String inventoryUpdateRoutingKey = "inventory.update";
    private final String inventoryRollbackRoutingKey = "inventory.rollback";

    @Override
    public OrderResponse findById(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOTFOUND));
        return OrderMapper.INSTANCE.toOrderResponse(order);
    }

    @Override
    public OrderResponse update(String id, OrderRequest orderRequest) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOTFOUND));
        order.setAddress(orderRequest.getAddress());
        order.setOrderStatus(orderRequest.getOrderStatus());
        order.setPaymentStatus(orderRequest.getPaymentStatus());
        return OrderMapper.INSTANCE.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public PagedModel<OrderResponse> findAllOrders(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(pageRequest);

        List<OrderResponse> orderResponses = orderPage.getContent()
                .stream()
                .map(OrderMapper.INSTANCE::toOrderResponse)
                .collect(Collectors.toList());
        return PagedModel.of(
                orderResponses,
                new PagedModel.PageMetadata(
                        orderPage.getSize(),
                        orderPage.getNumber(),
                        orderPage.getTotalElements()));
    }

    @Override
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper.INSTANCE::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> findOrdersByCustomerId(String id) {
        return orderRepository.getOrdersByCustomerId(id)
                .stream()
                .map(OrderMapper.INSTANCE::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponse createOrders(OrderCreateRequest orderCreateRequest, HttpServletRequest request) {

        // 1. Kiểm tra tồn kho cho tất cả sản phẩm (sync check)
        for (OrderCreateRequest.ProductDetailRequest productDetail : orderCreateRequest.getProductDetailOrders()) {
            boolean isAvailable = inventoryClient.checkStock(productDetail.getProductVariantDetailId(),
                    productDetail.getQuantity());
            if (!isAvailable)
                throw new AppException(ErrorCode.OUT_OF_STOCK);
        }

        // 2. Tạo đơn hàng
        Order order = Order.builder()
                .paymentStatus(orderCreateRequest.getPaymentStatus())
                .orderStatus(orderCreateRequest.getOrderStatus())
                .customerId(orderCreateRequest.getCustomerId())
                .systemUserId(orderCreateRequest.getSystemUserId())
                .createdAt(LocalDateTime.now())
                .address(orderCreateRequest.getAddress())
                .paymentMethod(PaymentMethod.valueOf(orderCreateRequest.getPaymentMethod().name()).name())
                .build();

        Order savedOrder = orderRepository.save(order);

        // 3. Tạo chi tiết đơn hàng
        List<OrderDetail> orderDetails = orderCreateRequest.getProductDetailOrders().stream().map(productDetail -> {
            return OrderDetail.builder()
                    .createdAt(LocalDateTime.now())
                    .order(savedOrder)
                    .price(productDetail.getPrice() * productDetail.getQuantity()) // Giá * số lượng
                    .quantity(productDetail.getQuantity())
                    .detailStatus(DetailStatus.PENDING)
                    .productVariantDetailId(productDetail.getProductVariantDetailId())
                    .build();
        }).collect(Collectors.toList());

        orderDetailRepository.saveAll(orderDetails);

        // 4. Tính tổng tiền đơn hàng
        Long totalAmount = orderDetails.stream()
                .mapToLong(orderDetail -> orderDetail.getPrice().longValue())
                .sum();

        // 5. Chuẩn hóa danh sách ProductVariantDetail cho OrderEvent
        List<OrderEvent.ProductVariantDetail> productVariantDetails = orderDetails.stream()
                .map(detail -> OrderEvent.ProductVariantDetail.builder()
                        .productVariantDetailId(detail.getProductVariantDetailId())
                        .quantity(detail.getQuantity())
                        .build())
                .collect(Collectors.toList());

        // 6. Lấy IP Address
        String ipAddress = getIpAddress(request);

        // 7. Tạo OrderEvent hoàn chỉnh
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(savedOrder.getId())
                .customerId(orderCreateRequest.getCustomerId())
                .paymentMethod(orderCreateRequest.getPaymentMethod().name()) // Enum to String
                .totalAmount(totalAmount)
                .ipAddress(ipAddress)
                .productVariantDetails(productVariantDetails)
                .build();

        // 8. Tạo CompletableFuture để chờ phản hồi
        CompletableFuture<PaymentStatusResponse> future = new CompletableFuture<>();
        orderResponseCache.put(savedOrder.getId(), future);

        // 9. Gửi message kèm reply queue để nhận phản hồi thanh toán (nếu VNPAY)
        rabbitTemplate.convertAndSend(orderExchange, orderCreatedRoutingKey, orderEvent, message -> {
            message.getMessageProperties().setReplyTo("order.reply.queue"); // Reply queue để nhận phản hồi
            return message;
        });

        // 10. Chờ phản hồi (timeout 30s)
        PaymentStatusResponse response;
        try {
            response = future.get(60, TimeUnit.SECONDS); // Có thể custom timeout theo config
        } catch (Exception e) {
            orderResponseCache.remove(savedOrder.getId()); // Dọn cache nếu timeout
            throw new AppException(ErrorCode.TIME_OUT, "Timeout waiting for payment link");
        }
        Collection<CustomerResponseV2> customerResponses = customerClient.getCustomerById(order.getCustomerId())
                .getData();
        CustomerResponseV2 customerResponse = customerResponses.stream().findFirst().orElse(null);

        // 11. Trả kết quả cho FE
        return OrderResponse.builder()
                .id(savedOrder.getId())
                .customer(customerResponse)
                .address(savedOrder.getAddress())
                .orderStatus(savedOrder.getOrderStatus())
                .paymentLink(response.getPaymentUrl())
                .paymentStatus(response.getStatus())
                .build();
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }

    @RabbitListener(queues = "${rabbitmq.queue.order-reply}")
    public void handlePaymentResponse(PaymentStatusResponse response) {
        log.info("Received payment response: {}", response);

        CompletableFuture<PaymentStatusResponse> future = orderResponseCache.get(response.getOrderId());
        if (future != null) {
            future.complete(response);
            orderResponseCache.remove(response.getOrderId());
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.payment-completed}")
    public void handlePaymentCompleted(PaymentStatusResponse response, Message message, Channel channel) throws IOException {
        log.info("Received payment status for order: {}", response.getOrderId());
        try {
        // 1. Lấy thông tin đơn hàng
        Order order = orderRepository.findById(response.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        // 2. Lấy chi tiết đơn hàng
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailsByOrderId(order.getId());
        List<InventoryUpdateMessage.ProductVariantDetail> productDetails = orderDetails.stream()
                .map(detail -> InventoryUpdateMessage.ProductVariantDetail.builder()
                        .productVariantDetailId(detail.getProductVariantDetailId())
                        .quantity(detail.getQuantity())
                        .build())
                .toList();

        // 3. Chuẩn bị message cập nhật tồn kho
        InventoryUpdateMessage inventoryUpdateMessage = InventoryUpdateMessage.builder()
                .orderId(order.getId())
                .productVariantDetails(productDetails)
                .build();

        // 4. Lấy thông tin khách hàng từ CustomerService
        Collection<CustomerResponseV2> customerResponses = customerClient.getCustomerById(order.getCustomerId()).getData();
        CustomerResponseV2 customerResponse = customerResponses.stream().findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        // 5. Chuẩn bị dữ liệu cho Email
        EmailRequest emailRequest = EmailRequest.builder()
                .paymentInfo("Thanh toán đơn hàng " + order.getId())
                .orderCode(order.getId())
                .shippingAddress(order.getAddress())
                .orderNumber(order.getId())
                .symbol("VND")
                .invoiceDate(LocalDateTime.now().toString())
                .invoiceNumber(order.getId())
                .customerName(customerResponse.getName()) // Sử dụng tên khách hàng thực tế
                .taxCode("123456789") // Cập nhật theo đúng thông tin doanh nghiệp nếu có
                .searchCode("123456789")
                .build();

        // 6. Xử lý theo trạng thái thanh toán
        if (PaymentStatus.PAID.equals(response.getStatus())) {
            log.info("Payment completed for order: {}", order.getId());

            // Cập nhật đơn hàng
            order.setPaymentStatus(PaymentStatus.PAID);
            order.setOrderStatus(OrderStatus.PROCESSING);
            orderRepository.save(order);

            // Gửi inventory.update để trừ tồn kho giữ
            rabbitTemplate.convertAndSend(orderExchange, inventoryUpdateRoutingKey, inventoryUpdateMessage);

        } else {
            log.warn("Payment failed for order: {}", order.getId());

            // Hủy đơn hàng
            order.setPaymentStatus(PaymentStatus.FAILED);
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);

            // Gửi rollback tồn kho
            rabbitTemplate.convertAndSend(orderExchange, inventoryRollbackRoutingKey, inventoryUpdateMessage);
        }

        // 7. Gửi Notification (email)
        NotificationDto notificationDto = NotificationDto.builder()
                .orderId(order.getId())
                .customerId(order.getCustomerId())
                .subject("Kết quả thanh toán đơn hàng #" + order.getId())
                .message(PaymentStatus.PAID.equals(response.getStatus())
                        ? "Đơn hàng của bạn đã thanh toán thành công!"
                        : "Thanh toán thất bại. Đơn hàng của bạn đã bị hủy.")
                .type(PaymentStatus.PAID.equals(response.getStatus()) ? "PAYMENT_PAID" : "PAYMENT_FAILED")
                .emailRequest(emailRequest)
                .email(customerResponse.getAccount().getEmail()) 
                .build();

        rabbitTemplate.convertAndSend(orderExchange, "notification", notificationDto);
        log.info("Notification sent to {}", customerResponse.getAccount().getEmail());
        } catch (DataIntegrityViolationException | ConstraintViolationException  e) {
            log.error("Constraint violation or Data integrity error for order {}: {}", response.getOrderId(), e.getMessage());

            // Reject message và đẩy qua DLQ (không retry tiếp)
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // false => không requeue
        } catch (Exception e) {
            log.error("Unexpected error when processing payment completed for order {}: {}", response.getOrderId(), e.getMessage());

            // Nếu lỗi khác (tạm thời), có thể requeue để retry
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // true => requeue để retry
        }
    }



}
