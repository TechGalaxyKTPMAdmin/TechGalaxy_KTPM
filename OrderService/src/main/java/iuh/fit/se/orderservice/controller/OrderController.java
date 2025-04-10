package iuh.fit.se.orderservice.controller;

import iuh.fit.se.orderservice.dto.request.OrderCreateRequest;
import iuh.fit.se.orderservice.dto.request.OrderRequest;
import iuh.fit.se.orderservice.dto.request.OrderUpdateRequest;
import iuh.fit.se.orderservice.dto.response.DataResponse;
import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.exception.AppException;
import iuh.fit.se.orderservice.exception.ErrorCode;
import iuh.fit.se.orderservice.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<DataResponse<OrderResponse>> createOrder(@RequestBody OrderCreateRequest request, HttpServletRequest httpRequest) {
        List<OrderResponse> orderResponses = List.of(orderService.createOrders(request,httpRequest));
        return ResponseEntity.ok(DataResponse.<OrderResponse>builder()
                .message("Create order success")
                .data(orderResponses)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<OrderResponse>> updateOrder(@PathVariable String id, @RequestBody OrderUpdateRequest orderUpdateRequest) {
        List<OrderResponse> orderResponses = List.of(orderService.update(id, orderUpdateRequest));
        return ResponseEntity.ok(DataResponse.<OrderResponse>builder()
                .message("Update order success")
                .data(orderResponses)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<OrderResponse>> getOrderById(@PathVariable String id) {
        OrderResponse orderResponse = orderService.findById(id);

//        System.out.println("57");
//        orderResponse.getOrderDetails().forEach(orderDetail -> {
//            System.out.println(orderDetail.getId());
//        });

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
