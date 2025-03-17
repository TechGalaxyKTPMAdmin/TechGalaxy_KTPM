package iuh.fit.se.orderservice.controller;

import iuh.fit.se.orderservice.dto.request.OrderRequest;
import iuh.fit.se.orderservice.dto.request.OrderCreateRequest;
import iuh.fit.se.orderservice.dto.response.DataResponse;
import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.exception.AppException;
import iuh.fit.se.orderservice.exception.ErrorCode;
import iuh.fit.se.orderservice.mapper.OrderMapper;
import iuh.fit.se.orderservice.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public ResponseEntity<DataResponse<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(DataResponse.<OrderResponse>builder()
                .message("Get all orders success")
                .data(orderService.findAll())
                .build());
    }

    @PostMapping
    public ResponseEntity<DataResponse<OrderResponse>> createOrder(@RequestBody OrderCreateRequest request, HttpServletRequest httpRequest) {
        List<OrderResponse> orderResponses = List.of(orderService.createOrders(request,httpRequest));
        return ResponseEntity.ok(DataResponse.<OrderResponse>builder()
                .message("Create order success")
                .data(orderResponses)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<OrderResponse>> updateOrder(@PathVariable String id, @RequestBody OrderRequest request) {
        System.out.println(request.getOrderStatus());
        System.out.println(request.getPaymentStatus());
        System.out.println(request.getAddress());

        List<OrderResponse> orderResponses = List.of(orderService.update(id, request));
        return ResponseEntity.ok(DataResponse.<OrderResponse>builder()
                .message("Update order success")
                .data(orderResponses)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<OrderResponse>> getOrderById(@PathVariable String id) {
        OrderResponse orderResponse = orderService.findById(id);
        if (orderResponse == null)
            throw new AppException(ErrorCode.ORDER_NOTFOUND);

        return ResponseEntity.ok(DataResponse.<OrderResponse>builder()
                .message("Get order by id success")
                .data(List.of(orderResponse))
                .build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<DataResponse<OrderResponse>> getOrdersByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(DataResponse.<OrderResponse>builder()
                .message("Get orders by customer id success")
                .data(orderService.findOrdersByCustomerId(customerId))
                .build());
    }

}
