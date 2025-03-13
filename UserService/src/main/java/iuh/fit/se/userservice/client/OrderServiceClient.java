package iuh.fit.se.userservice.client;

import iuh.fit.se.userservice.dto.response.DataResponse;
import iuh.fit.se.userservice.dto.response.OrderResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "OrderService")
public interface OrderServiceClient {

    @GetMapping("/orders/customer/{customerId}")
    ResponseEntity<DataResponse<OrderResponse>> getOrdersByCustomerId(@PathVariable("customerId") String customerId);
}