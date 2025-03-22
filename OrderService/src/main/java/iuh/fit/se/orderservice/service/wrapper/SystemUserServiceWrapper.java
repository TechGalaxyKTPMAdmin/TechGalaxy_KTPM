//package iuh.fit.se.orderservice.service.wrapper;
//
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import io.github.resilience4j.retry.annotation.Retry;
//import iuh.fit.se.orderservice.client.SystemUserClient;
//import iuh.fit.se.orderservice.client.SystemUSerClient;
//import iuh.fit.se.orderservice.dto.response.SystemUserResponseV2;
//import iuh.fit.se.orderservice.dto.response.DataResponse;
//import iuh.fit.se.orderservice.dto.response.SystemUserResponse;
//import iuh.fit.se.orderservice.exception.AppException;
//import iuh.fit.se.orderservice.exception.ErrorCode;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class SystemUserServiceWrapper {
//
//    private final SystemUSerClient systemUSerClient;
//
//    @CircuitBreaker(name = "customerService", fallbackMethod = "getSystemUserFallback")
//    @Retry(name = "SystemUserService")
//    public Collection<SystemUserResponse> getSystemUserById(String SystemUserId) {
//        log.info("🔍 Calling SystemUserService for SystemUserId: {}", SystemUserId);
//        DataResponse<SystemUserResponse> response = systemUSerClient.getSystemUserById(SystemUserId);
//        return response.getData();
//    }
//
//    // Fallback khi lỗi
//    public Collection<SystemUserResponse> getSystemUserFallback(String SystemUserId, Throwable throwable) {
//        log.error("❌ SystemUserService failed for SystemUserId: {}. Reason: {}", SystemUserId, throwable.getMessage());
//        throw new AppException(ErrorCode.SystemUser_NOT_FOUND, "SystemUser Service is unavailable. Please try later.");
//    }
//}
