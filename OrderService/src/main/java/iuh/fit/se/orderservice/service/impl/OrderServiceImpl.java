package iuh.fit.se.orderservice.service.impl;

import iuh.fit.se.orderservice.dto.request.OrderCreateRequest;
import iuh.fit.se.orderservice.dto.request.OrderRequest;
import iuh.fit.se.orderservice.dto.response.CustomerResponse;
import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.dto.response.SystemUserResponse;
import iuh.fit.se.orderservice.entity.Order;
import iuh.fit.se.orderservice.entity.OrderDetail;
import iuh.fit.se.orderservice.entity.enumeration.DetailStatus;
import iuh.fit.se.orderservice.entity.enumeration.OrderStatus;
import iuh.fit.se.orderservice.entity.enumeration.PaymentStatus;
import iuh.fit.se.orderservice.exception.AppException;
import iuh.fit.se.orderservice.exception.ErrorCode;
import iuh.fit.se.orderservice.mapper.OrderMapper;
import iuh.fit.se.orderservice.repository.OrderDetailRepository;
import iuh.fit.se.orderservice.repository.OrderRepository;
import iuh.fit.se.orderservice.service.OrderDetailService;
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

import java.time.LocalDateTime;
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
    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderServiceImpl(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, RabbitMQSenderService rabbitMQSenderService, RestTemplate restTemplate, OrderDetailService orderDetailService) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.rabbitMQSenderService = rabbitMQSenderService;
        this.restTemplate = restTemplate;
        this.orderDetailService = orderDetailService;
    }

    @Override
    public OrderResponse findById(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOTFOUND));
        return OrderMapper.INSTANCE.toOrderResponse(order);
    }

    @Override
    @Transactional
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


    @Override
    @Transactional
    public OrderResponse createOrders(OrderCreateRequest orderCreateRequest) {
//        CustomerResponse customer = restTemplate.getForObject(localhost.., CustomerResponse.class, orderCreateRequest.getId())
        CustomerResponse customer = new CustomerResponse();
        customer.setId("008b425f-fcbe-4b3a-952c-555835a4dc2c");

        if (customer == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOTFOUND);
        }

//        SystemUserResponse systemUser = restTemplate.getForObject()
        SystemUserResponse systemUser = new SystemUserResponse();
        systemUser.setId("046a91e9-dfc3-4715-934d-53c427e1f992");
        if (systemUser == null) {
            throw new AppException(ErrorCode.SYSTEM_USER_NOTFOUND);
        }

        Order order = new Order();
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderStatus(OrderStatus.NEW);
        order.setCustomerId(customer.getId());
        order.setSystemUserId(systemUser.getId());
        order.setCreatedAt(LocalDateTime.now());
        order.setAddress(orderCreateRequest.getAddress());

        Order savedOrder = orderRepository.save(order);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderCreateRequest.ProductDetailRequest productDetail : orderCreateRequest.getProductDetailOrders()) {
//            restTemplate.getForObject(localhost.., ProductDetailRequest.class, productDetail.getProductVariantDetailId()..
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setCreatedAt(LocalDateTime.now());
            orderDetail.setOrder(savedOrder);
            orderDetail.setPrice(productDetail.getPrice() * productDetail.getQuantity());
            orderDetail.setQuantity(productDetail.getQuantity());
            orderDetail.setDetailStatus(DetailStatus.PENDING);
            orderDetail.setProductVariantDetailId(productDetail.getProductVariantDetailId());
            orderDetails.add(orderDetail);
        }

        orderDetailRepository.saveAll(orderDetails);
        OrderResponse orderResponse = OrderMapper.INSTANCE.toOrderResponse(savedOrder);
        rabbitMQSenderService.sendOrderCreatedEvent(orderResponse);

        return orderResponse;
    }

}
