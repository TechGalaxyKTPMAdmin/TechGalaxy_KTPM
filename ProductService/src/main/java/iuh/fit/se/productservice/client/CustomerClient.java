package iuh.fit.se.productservice.client;

import iuh.fit.se.productservice.dto.response.CustomerResponseV2;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import iuh.fit.se.productservice.dto.response.DataResponse;

@FeignClient(name = "UserService", configuration = iuh.fit.se.productservice.config.FeignConfig.class)
public interface CustomerClient {
    @GetMapping("/{id}")
    DataResponse<CustomerResponseV2> getCustomerById(@PathVariable("id") String id);

}