package iuh.fit.se.orderservice.service.impl;

import iuh.fit.se.orderservice.dto.response.CustomerResponseV2;
import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.dto.response.SystemUserResponse;
import iuh.fit.se.orderservice.mapper.OrderMapper;
import iuh.fit.se.orderservice.repository.OrderRepository;
import iuh.fit.se.orderservice.service.wrapper.CustomerServiceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderCacheService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerServiceWrapper customerServiceWrapper;

    @Autowired
    public OrderCacheService(OrderRepository orderRepository, OrderMapper orderMapper, CustomerServiceWrapper customerServiceWrapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.customerServiceWrapper = customerServiceWrapper;
    }

    @CachePut(value = "OrderResponses", key = "'findAll'")
    public List<OrderResponse> updateFindAllCache() {
        System.out.println("Dong nay test ne");
        System.out.println(customerServiceWrapper);
        System.out.println("Dong nay test ne");
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
}

