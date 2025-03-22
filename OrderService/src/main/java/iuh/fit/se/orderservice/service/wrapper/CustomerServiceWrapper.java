package iuh.fit.se.orderservice.service.wrapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import iuh.fit.se.orderservice.client.CustomerClient;
import iuh.fit.se.orderservice.dto.response.CustomerResponseV2;
import iuh.fit.se.orderservice.dto.response.DataResponse;
import iuh.fit.se.orderservice.dto.response.SystemUserResponse;
import iuh.fit.se.orderservice.exception.AppException;
import iuh.fit.se.orderservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceWrapper {

    private final CustomerClient customerClient;

    @CircuitBreaker(name = "customerService", fallbackMethod = "getCustomerFallback")
    @Retry(name = "customerService")
    public Collection<CustomerResponseV2> getCustomerById(String customerId) {
        log.info("🔍 Calling CustomerService for customerId: {}", customerId);
        DataResponse<CustomerResponseV2> response = customerClient.getCustomerById(customerId);
        return response.getData();
    }


    @CircuitBreaker(name = "systemUserService", fallbackMethod = "getSystemUserFallback")
    @Retry(name = "systemUserService")
    public Collection<SystemUserResponse> getSystemUserById(String systemUserId) {
        log.info("🔍 Calling System User Service for systemUserId: {}", systemUserId);
        DataResponse<SystemUserResponse> response = customerClient.getSystemUserById(systemUserId);
        return response.getData();
    }

    // Fallback khi lỗi
    public Collection<CustomerResponseV2> getCustomerFallback(String customerId, Throwable throwable) {
        log.error("❌ CustomerService failed for customerId: {}. Reason: {}", customerId, throwable.getMessage());
        throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND, "Customer Service is unavailable. Please try later.");
    }

    public Collection<CustomerResponseV2> getSystemUserFallback(String systemUserId, Throwable throwable) {
        log.error("❌ System User Service failed for systemUserId: {}. Reason: {}", systemUserId, throwable.getMessage());
        throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND, "System User Service is unavailable. Please try later.");
    }
}
