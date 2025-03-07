package iuh.fit.se.orderservice.service.impl;

import iuh.fit.se.orderservice.dto.request.OrderRequest;
import iuh.fit.se.orderservice.dto.request.OrderCreateRequest;
import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.entity.Order;
import iuh.fit.se.orderservice.entity.OrderDetail;
import iuh.fit.se.orderservice.entity.enumeration.DetailStatus;
import iuh.fit.se.orderservice.entity.enumeration.OrderStatus;
import iuh.fit.se.orderservice.exception.AppException;
import iuh.fit.se.orderservice.exception.ErrorCode;
import iuh.fit.se.orderservice.mapper.OrderMapper;
import iuh.fit.se.orderservice.repository.OrderDetailRepository;
import iuh.fit.se.orderservice.repository.OrderRepository;
import iuh.fit.se.orderservice.service.OrderService;
import iuh.fit.se.orderservice.service.RabbitMQSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final RestTemplate restTemplate;
    private final RabbitMQSenderService rabbitMQSenderService;

    @Autowired
    public OrderServiceImpl(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, RabbitMQSenderService rabbitMQSenderService, RestTemplate restTemplate) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.rabbitMQSenderService = rabbitMQSenderService;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional
    public OrderResponse save(OrderRequest orderRequest) {
        Order order = orderRepository.save(OrderMapper.INSTANCE.toOrderFromRequest(orderRequest));
        return OrderMapper.INSTANCE.toOrderResponse(order);
    }

    @Override
    public OrderResponse findById(String id) {
        Order order = orderRepository.findById(id).orElse(null);
        return OrderMapper.INSTANCE.toOrderResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse update(String id, OrderRequest orderRequest) {
        if (!orderRepository.existsById(id))
            return null;
        Order order = orderRepository.save(OrderMapper.INSTANCE.toOrderFromRequest(orderRequest));
        return OrderMapper.INSTANCE.toOrderResponse(order);
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
                        orderPage.getTotalElements()
                )
        );
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


    @Transactional
    @Override
    public OrderResponse createOrders(OrderCreateRequest orderCreateRequest) {
        Customer customer = customerRepository.findById(orderCreateRequest.getCustomerId()).orElseThrow(
                () -> new AppException(ErrorCode.CUSTOMER_NOTFOUND)
        );

        Order.OrderBuilder orderBuilder = Order.builder()
                .customer(customer)
                .paymentStatus(orderCreateRequest.getPaymentStatus())
                .address(orderCreateRequest.getAddress())
                .orderStatus(OrderStatus.NEW);
        if (orderCreateRequest.getSystemUserId() != null && !orderCreateRequest.getSystemUserId().isEmpty()) {
            SystemUser systemUser = systemUserRepository.findById(orderCreateRequest.getSystemUserId()).orElseThrow(
                    () -> new AppException(ErrorCode.SYSTEM_USER_NOTFOUND)
            );
            orderBuilder.orderStatus(OrderStatus.COMPLETED)
                    .systemUser(systemUser);
        }
        Order order = orderBuilder.build();
        orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderCreateRequest.ProductDetailRequest productDetail : orderCreateRequest.getProductDetailOrders()) {
            ProductVariantDetail productVariantDetail = productVariantDetailRepository.findById(productDetail.getProductVariantDetailId()).orElseThrow(
                    () -> new AppException(ErrorCode.PRODUCT_NOTFOUND)
            );

            if (productVariantDetail.getQuantity() < productDetail.getQuantity()){
                throw new AppException(ErrorCode.INSUFFICIENT_PRODUCT_QUANTITY, "Product: " + productVariantDetail.getProductVariant().getName() + " - Quantity: " + productVariantDetail.getQuantity());
            }
            if (productDetail.getQuantity() <= 0){
                throw new AppException(ErrorCode.INVALID_PRODUCT_QUANTITY, "Product quantity: " + productDetail.getQuantity());
            }
            double discountedPrice = (1 - productVariantDetail.getSale()) * productVariantDetail.getPrice();
            double totalPrice = discountedPrice * productDetail.getQuantity();

            orderDetails.add(OrderDetail.builder()
                    .order(order)
                    .detailStatus(DetailStatus.PROCESSING)
                    .productVariantDetail(productVariantDetail)
                    .quantity(productDetail.getQuantity())
                    .price(totalPrice)
                    .build());
        }

        orderDetailRepository.saveAll(orderDetails);

        // Cập nhật số lượng sản phẩm trong kho sau khi lưu tất cả OrderDetails
        for (OrderCreateRequest.ProductDetailRequest productDetail : orderCreateRequest.getProductDetailOrders()) {
            ProductVariantDetail productVariantDetail = productVariantDetailRepository.findById(productDetail.getProductVariantDetailId()).orElseThrow(
                    () -> new AppException(ErrorCode.PRODUCT_NOTFOUND)
            );
            productVariantDetail.setQuantity(productVariantDetail.getQuantity() - productDetail.getQuantity());
            productVariantDetailRepository.save(productVariantDetail);
        }

        OrderResponse orderResponse = OrderMapper.INSTANCE.toOrderResponse(order);

        return orderResponse;
    }


}
