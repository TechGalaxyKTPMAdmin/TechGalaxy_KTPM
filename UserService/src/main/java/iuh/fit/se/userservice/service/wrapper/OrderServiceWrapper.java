package iuh.fit.se.userservice.service.wrapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import iuh.fit.se.userservice.client.OrderServiceClient;
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
public class OrderServiceWrapper {

    private final OrderServiceClient orderServiceClient;

    @CircuitBreaker(name = "orderService", fallbackMethod = "getOrdersFallback")
    @Retry(name = "orderService")
    public Collection<OrderResponse> getOrdersByCustomerId(String customerId) {
        log.info("🔍 [UserService] Calling OrderService for customerId: {}", customerId);

        ResponseEntity<DataResponse<OrderResponse>> response = orderServiceClient.getOrdersByCustomerId(customerId);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new AppException(ErrorCode.ORDER_NOTFOUND, "Orders not found for customer: " + customerId);
        }

        return response.getBody().getData();
    }

    public Collection<OrderResponse> getOrdersFallback(String customerId, Throwable throwable) {
        log.error("❌ [Fallback] OrderService failed for customerId: {}. Reason: {}", customerId, throwable.getMessage());

        throw new AppException(ErrorCode.ORDER_SERVICE_UNAVAILABLE,
                "Order Service is temporarily unavailable. Please try again later.");
    }
}
