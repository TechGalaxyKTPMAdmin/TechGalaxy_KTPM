package iuh.fit.se.orderservice.client;

import iuh.fit.se.orderservice.dto.response.CustomerResponseV2;
import iuh.fit.se.orderservice.dto.response.SystemUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import iuh.fit.se.orderservice.dto.response.DataResponse;

@FeignClient(name = "UserService")
public interface CustomerClient {
    @GetMapping("/customers/{id}")
    DataResponse<CustomerResponseV2> getCustomerById(@PathVariable("id") String id);

    @GetMapping("/system-users/{id}")
    DataResponse<SystemUserResponse> getSystemUserById(@PathVariable("id") String id);
}
