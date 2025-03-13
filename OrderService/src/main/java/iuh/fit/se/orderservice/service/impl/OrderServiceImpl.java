package iuh.fit.se.orderservice.service.impl;

import iuh.fit.se.orderservice.dto.request.OrderCreateRequest;
import iuh.fit.se.orderservice.dto.request.OrderRequest;
import iuh.fit.se.orderservice.dto.response.CustomerResponse;
import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.dto.response.SystemUserResponse;
import iuh.fit.se.orderservice.entity.Order;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final RestTemplate restTemplate;
    private final RabbitMQSenderService rabbitMQSenderService;
    private final OrderMapper orderMapper;
    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderServiceImpl(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, RabbitMQSenderService rabbitMQSenderService, RestTemplate restTemplate, OrderMapper orderMapper, OrderDetailService orderDetailService) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.rabbitMQSenderService = rabbitMQSenderService;
        this.restTemplate = restTemplate;
        this.orderMapper = orderMapper;
        this.orderDetailService = orderDetailService;
    }

//    @Override
//    @Transactional
//    public OrderResponse save(OrderRequest orderRequest) {
//        Order order = orderRepository.save(OrderMapper.INSTANCE.toOrderFromRequest(orderRequest));

//        rabbitMQSenderService.sendOrderCreatedEvent(...);
//        return OrderMapper.INSTANCE.toOrderResponse(order);
//    }

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
        order.setOrderStatus(OrderStatus.NEW);
        order.setCustomerId(customer.getId());
        order.setSystemUserId(systemUser.getId());
        order.setCreatedAt(LocalDateTime.now());
        order.setAddress(orderCreateRequest.getAddress());
        order.setPaymentStatus(PaymentStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        List<OrderCreateRequest.ProductDetailRequest> productDetails = orderCreateRequest.getProductDetailOrders();
        if (!orderDetailService.save(productDetails, order))
            return null;
//        rabbitMQSenderService.sendOrderCreatedEvent(mapToOrderResponse);

        return orderMapper.toOrderResponse(order);
    }


}
