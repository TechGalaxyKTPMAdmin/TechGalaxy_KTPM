package iuh.fit.se.orderservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import iuh.fit.se.orderservice.dto.request.*;
import iuh.fit.se.orderservice.dto.response.*;
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
import iuh.fit.se.orderservice.service.OrderService;
import iuh.fit.se.orderservice.service.wrapper.CustomerServiceWrapper;
import iuh.fit.se.orderservice.service.wrapper.InventoryServiceWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final InventoryServiceWrapper inventoryServiceWrapper;
    private final CustomerServiceWrapper customerServiceWrapper;
    // private final SystemUserServiceWrapper systemUserServiceWrapper;
    private final OrderResponseCache orderResponseCache;
    private final RabbitTemplate rabbitTemplate;
    private final OrderMapper orderMapper;
    @Qualifier("redisObjectMapper")
    private final ObjectMapper objectMapper;

    private final String orderExchange = "order.exchange";
    private final String orderCreatedRoutingKey = "order.created";
    private final String inventoryUpdateRoutingKey = "inventory.update";
    private final String inventoryRollbackRoutingKey = "inventory.rollback";

    @Override
    @Cacheable(value = "OrderResponses", key = "#id", unless = "#result == null")
    public OrderResponse findById(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOTFOUND));
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailsByOrderId(order.getId());
        order.setOrderDetails(orderDetails);
        orderDetails.forEach(orderDetail -> {
            System.out.println("test");
            System.out.println(orderDetail.getId());
        });
        return objectMapper.convertValue(orderMapper.toOrderResponse(order), OrderResponse.class);
    }

    @Override
    @CacheEvict(value = "OrderResponses", key = "#id")
    public OrderResponse update(String id, OrderUpdateRequest orderRequest) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOTFOUND));

        Collection<CustomerResponseV2> customerResponses = customerServiceWrapper
                .getCustomerById(orderRequest.getCustomer().getId());
        customerResponses.stream().findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        boolean orderStatusValid = isValidOrderStatus(order.getOrderStatus(), orderRequest.getOrderStatus());
        boolean paymentStatusValid = isValidPaymentStatus(order.getPaymentStatus(), orderRequest.getPaymentStatus());
        if (!orderStatusValid) {
            throw new AppException(ErrorCode.ORDER_STATUS_INVALID,
                    "Trạng thái đơn hàng không hợp lệ: Không thể chuyển từ " + order.getOrderStatus() + " thành " + orderRequest.getOrderStatus() + ".");
        }
        if (!paymentStatusValid) {
            throw new AppException(ErrorCode.PAYMENT_STATUS_INVALID,
                    "Trạng thái thanh toán không hợp lệ: Không thể chuyển từ " + order.getPaymentStatus() + " thành " + orderRequest.getPaymentStatus() + ".");
        }

        order.setAddress(orderRequest.getAddress());
        order.setOrderStatus(orderRequest.getOrderStatus());
        order.setPaymentStatus(orderRequest.getPaymentStatus());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        Order orderSaved = orderRepository.save(order);

        updateFindAllCache();
        return orderMapper.toOrderResponse(orderSaved);
    }

    @CachePut(value = "OrderResponses", key = "'findAll'")
    public List<OrderResponse> updateFindAllCache() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "OrderResponses", key = "'findAll'", unless = "#result.isEmpty()")
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .map(orderResponse -> {
                    Collection<CustomerResponseV2> customerResponses = customerServiceWrapper
                            .getCustomerById(orderResponse.getCustomer().getId());
                    CustomerResponseV2 customerResponse = customerResponses.stream().findFirst().orElse(null);
                    assert customerResponse != null;
                    orderResponse.getCustomer().setName(customerResponse.getName());

                    Collection<SystemUserResponse> systemUserResponses = customerServiceWrapper
                            .getSystemUserById(orderResponse.getSystemUser().getId());
                    SystemUserResponse systemUserResponse = systemUserResponses.stream().findFirst().orElse(null);
                    assert systemUserResponse != null;
                    orderResponse.getSystemUser().setName(systemUserResponse.getName());
                    return orderResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PagedModel<OrderResponse> findAllOrders(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(pageRequest);

        List<OrderResponse> orderResponses = orderPage.getContent()
                .stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
        return PagedModel.of(
                orderResponses,
                new PagedModel.PageMetadata(
                        orderPage.getSize(),
                        orderPage.getNumber(),
                        orderPage.getTotalElements()));
    }

    @Override
    public List<OrderResponse> findOrdersByCustomerId(String id) {
        return orderRepository.getOrdersByCustomerId(id)
                .stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponse createOrders(OrderCreateRequest orderCreateRequest, HttpServletRequest request) {

        // 1. Kiểm tra tồn kho cho tất cả sản phẩm (sync check)
        // Chỉ cần 1 sản phẩm không đủ số lượng => thông báo lỗi
        for (OrderCreateRequest.ProductDetailRequest productDetail : orderCreateRequest.getProductDetailOrders()) {
            boolean isAvailable = inventoryServiceWrapper.checkStock(productDetail.getProductVariantDetailId(),
                    productDetail.getQuantity());
            if (!isAvailable) {
                throw new AppException(ErrorCode.OUT_OF_STOCK,
                        "Sản phẩm đã hết hàng: " + productDetail.getProductVariantDetailId());
            }
        }

        // 2. Tạo đơn hàng
        Order order = Order.builder()
                .paymentStatus(orderCreateRequest.getPaymentStatus())
                .orderStatus(orderCreateRequest.getOrderStatus())
                .customerId(orderCreateRequest.getCustomerId())
                .systemUserId(orderCreateRequest.getSystemUserId())
                .createdAt(LocalDateTime.now())
                .address(orderCreateRequest.getAddress())
                .paymentMethod(orderCreateRequest.getPaymentMethod())
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

        // 5. Chuẩn hóa danh sách ProductVariantDetails cho OrderEvent
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
            response = future.get(30, TimeUnit.SECONDS); // Có thể custom timeout theo config
        } catch (Exception e) {
            orderResponseCache.remove(savedOrder.getId()); // Dọn cache nếu timeout
            throw new AppException(ErrorCode.TIME_OUT, "Timeout waiting for payment link");
        }
        System.out.println(savedOrder.getCustomerId());
        Collection<CustomerResponseV2> customerResponses = customerServiceWrapper
                .getCustomerById(savedOrder.getCustomerId());
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

    @RabbitListener(queues = "payment.completed.queue")
    public void handlePaymentCompleted(PaymentStatusResponse response) {
        log.info("Received payment status for order: {}", response.getOrderId());

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
        Collection<CustomerResponseV2> customerResponses = customerServiceWrapper
                .getCustomerById(order.getCustomerId());
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
    }

    @CacheEvict(value = "Products", allEntries = true)
    public void clearCache() {}

    public boolean isValidPaymentStatus(PaymentStatus paymentStatusBefore, PaymentStatus paymentStatusAfter) {
        // Nếu trạng thái trước và sau giống nhau thì hợp lệ
        if (paymentStatusBefore == paymentStatusAfter) {
            return true;
        }

        // Các quy tắc chuyển đổi trạng thái
        switch (paymentStatusBefore) {
            case PENDING:
                // PENDING có thể chuyển thành bất kỳ trạng thái nào khác
                return true;

            case WAITING:
                // WAITING có thể chuyển thành PAID, FAILED, hoặc CANCELLED
                return paymentStatusAfter == PaymentStatus.PAID ||
                        paymentStatusAfter == PaymentStatus.FAILED ||
                        paymentStatusAfter == PaymentStatus.CANCELLED;

            case PAID:
                // PAID chỉ có thể chuyển thành REFUNDED
                return paymentStatusAfter == PaymentStatus.REFUNDED;

            case FAILED:
                // FAILED có thể chuyển thành PENDING (thử lại) hoặc CANCELLED
                return paymentStatusAfter == PaymentStatus.PENDING ||
                        paymentStatusAfter == PaymentStatus.CANCELLED;

            case REFUNDED:
                // REFUNDED là trạng thái cuối, không thể chuyển sang trạng thái khác
                return false;

            case CANCELLED:
                // CANCELLED là trạng thái cuối, không thể chuyển sang trạng thái khác
                return false;

            default:
                return false;
        }
    }

    public boolean isValidOrderStatus(OrderStatus orderStatusBefore, OrderStatus orderStatusAfter) {
        // Nếu trạng thái trước và sau giống nhau thì hợp lệ
        if (orderStatusBefore == orderStatusAfter) {
            return true;
        }

        // Các quy tắc chuyển đổi trạng thái
        switch (orderStatusBefore) {
            case NEW:
                // NEW có thể chuyển thành PROCESSING, CONFIRMED hoặc CANCELLED
                return orderStatusAfter == OrderStatus.PROCESSING ||
                        orderStatusAfter == OrderStatus.CONFIRMED ||
                        orderStatusAfter == OrderStatus.CANCELLED;

            case PROCESSING:
                // PROCESSING có thể chuyển thành COMPLETED hoặc CANCELLED
                return orderStatusAfter == OrderStatus.COMPLETED ||
                        orderStatusAfter == OrderStatus.CANCELLED;

            case CONFIRMED:
                // CONFIRMED có thể chuyển thành PROCESSING hoặc CANCELLED
                return orderStatusAfter == OrderStatus.PROCESSING ||
                        orderStatusAfter == OrderStatus.CANCELLED;

            case COMPLETED:
                // COMPLETED là trạng thái cuối, không thể chuyển sang trạng thái khác
                return false;

            case CANCELLED:
                // CANCELLED là trạng thái cuối, không thể chuyển sang trạng thái khác
                return false;

            default:
                return false;
        }
    }

}
