package iuh.fit.se.userservice;

import iuh.fit.se.userservice.dto.response.OrderResponse;
import iuh.fit.se.userservice.service.OrderClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class TestController {
    private final OrderClientService orderClientService;

    TestController(OrderClientService orderClientService) {
        this.orderClientService = orderClientService;
    }
    @GetMapping("/test")
    public String test() {
        return "Hello World from User Service!";
    }

    @GetMapping("/auth/test2")
    public String test2() {
        System.out.println("CustomerOrderController.getCustomerOrders");
        List<OrderResponse> orders = orderClientService.getOrdersByCustomerId("2a965310-71c4-4d25-a257-257edd815e3a");
        int count = orders.size();
        return String.format("There are %d orders", count);
    }
}
