package iuh.fit.se.userservice.service.wrapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import iuh.fit.se.userservice.client.NotificationServiceClient;
import iuh.fit.se.userservice.client.OrderServiceClient;
import iuh.fit.se.userservice.dto.request.EmailRequestRegister;
import iuh.fit.se.userservice.dto.response.DataResponse;
import iuh.fit.se.userservice.dto.response.OrderResponse;
import iuh.fit.se.userservice.exception.AppException;
import iuh.fit.se.userservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceWrapper {

    private final NotificationServiceClient notificationServiceClient;

    @CircuitBreaker(name = "notificationService", fallbackMethod = "sendSuccessRegistrationEmailFallback")
    @Retry(name = "notificationService")
    public String sendSuccessRegistrationEmail(EmailRequestRegister request) {
        log.info("🔍 [notificationService] Calling notificationService for EmailRequestRegister: {}", request);
        String response = notificationServiceClient.sendSuccessRegistrationEmail(request);
        log.info("🔍 [notificationService] result EmailRequestRegister: {}", response);

        return response;
    }

    public String sendSuccessRegistrationEmailFallback(EmailRequestRegister request,
            Throwable throwable) {

        log.error("❌ [Fallback] [notificationService] failed for send Success  RegistrationEmail: {}. Reason: {}",
                request,
                throwable.getMessage());

        throw new AppException(ErrorCode.NOTIFICATION_SERVICE_UNAVAILABLE,
                "Notification Service is temporarily unavailable. Please try again later.");
    }
}
