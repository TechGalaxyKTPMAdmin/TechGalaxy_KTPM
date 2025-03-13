package iuh.fit.se.userservice.service;

import java.util.List;

import iuh.fit.se.userservice.dto.response.OrderResponse;

public interface OrderClientService {
    List<OrderResponse> getOrdersByCustomerId(String customerId);
}