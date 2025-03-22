//package iuh.fit.se.orderservice.client;
//
//import iuh.fit.se.orderservice.dto.response.CustomerResponseV2;
//import iuh.fit.se.orderservice.dto.response.DataResponse;
//import iuh.fit.se.orderservice.dto.response.SystemUserResponse;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@FeignClient(name = "UserService", path = "/system-users")
//public interface SystemUSerClient {
//    @GetMapping("/{id}")
//    DataResponse<SystemUserResponse> getSystemUserById(@PathVariable("id") String id);
//}
