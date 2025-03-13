package iuh.fit.se.orderservice.controller;

import iuh.fit.se.orderservice.dto.request.OrderRequest;
import iuh.fit.se.orderservice.dto.request.OrderCreateRequest;
import iuh.fit.se.orderservice.dto.response.DataResponse;
import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.exception.AppException;
import iuh.fit.se.orderservice.exception.ErrorCode;
import iuh.fit.se.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<DataResponse<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(DataResponse.<OrderResponse>builder()
                .message("Get all orders success")
                .data(orderService.findAll())
                .build());
    }

    @PostMapping
    public ResponseEntity<DataResponse<OrderResponse>> createOrder(@RequestBody OrderCreateRequest request) {
        List<OrderResponse> orderResponses = List.of(orderService.createOrders(request));
        return ResponseEntity.ok(DataResponse.<OrderResponse>builder()
                .message("Create order success")
                .data(orderResponses)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<OrderResponse>> updateOrder(@PathVariable String id, @RequestBody OrderRequest request) {
        List<OrderResponse> orderResponses = List.of(orderService.update(id, request));
        return ResponseEntity.ok(DataResponse.<OrderResponse>builder()
                .message("Update order success")
                .data(orderResponses)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<OrderResponse>> getOrderById(@PathVariable String id) {
        if (orderService.findById(id) == null)
            throw new AppException(ErrorCode.ORDER_NOTFOUND);
        List<OrderResponse> orderResponses = List.of(orderService.findById(id));
        return ResponseEntity.ok(DataResponse.<OrderResponse>builder()
                .message("Get order by id success")
                .data(orderResponses)
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
