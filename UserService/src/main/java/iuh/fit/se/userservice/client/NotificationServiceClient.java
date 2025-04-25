package iuh.fit.se.userservice.client;

import iuh.fit.se.userservice.dto.request.EmailRequestRegister;
import iuh.fit.se.userservice.dto.response.DataResponse;
import iuh.fit.se.userservice.dto.response.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NotificationService")
public interface NotificationServiceClient {

    @PostMapping("/email/success-registration")
    public String sendSuccessRegistrationEmail(@RequestBody EmailRequestRegister request);
}