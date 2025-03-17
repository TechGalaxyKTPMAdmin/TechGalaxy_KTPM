package iuh.fit.se.orderservice.service;

import iuh.fit.se.orderservice.dto.request.OrderRequest;
import iuh.fit.se.orderservice.dto.request.OrderCreateRequest;
import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.entity.Order;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.PagedModel;

import java.util.List;

public interface OrderService {
//    OrderResponse save(OrderRequest orderRequest);

    OrderResponse findById(String id);

    OrderResponse update(String id, OrderRequest orderRequest);

    PagedModel<OrderResponse> findAllOrders(int page, int size);

    List<OrderResponse> findAll();

    List<OrderResponse> findOrdersByCustomerId(String id);

    OrderResponse createOrders(OrderCreateRequest orderCreateRequest, HttpServletRequest request);
}
