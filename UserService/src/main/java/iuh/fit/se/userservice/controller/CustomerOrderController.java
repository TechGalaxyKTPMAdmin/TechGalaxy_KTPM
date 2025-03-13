package iuh.fit.se.userservice.controller;


import iuh.fit.se.userservice.dto.response.OrderResponse;
import iuh.fit.se.userservice.service.OrderClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerOrderController {

    private final OrderClientService orderClientService;

    @GetMapping("/orders/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getCustomerOrders(@PathVariable String customerId) {
        System.out.println("CustomerOrderController.getCustomerOrders");
        List<OrderResponse> orders = orderClientService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }
}