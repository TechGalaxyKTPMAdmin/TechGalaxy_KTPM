package iuh.fit.se.productservice.service.wrapper;

import java.util.Collection;

import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import iuh.fit.se.productservice.client.InventoryClient;
import iuh.fit.se.productservice.dto.request.InventoryRequest;
import iuh.fit.se.productservice.dto.response.DataResponse;
import iuh.fit.se.productservice.dto.response.InventoryResponseAdd;
import iuh.fit.se.productservice.exception.AppException;
import iuh.fit.se.productservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceWrapper {
    private final InventoryClient inventoryClient;

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "addInventoryFallback")
    @Retry(name = "customerService")
    public Collection<InventoryResponseAdd> saveInventory(InventoryRequest inventoryRequest) {
        log.info("🔍 Calling inventoryService for inventoryRequest: {}", inventoryRequest);
        DataResponse<InventoryResponseAdd> response = inventoryClient.addInventory(inventoryRequest);
        return response.getData();
    }

    // Fallback khi lỗi
    public Collection<InventoryResponseAdd> addInventoryFallback(
            InventoryRequest inventoryRequest, Throwable throwable) {
        log.error("❌ CustomerService failed for add inventoryRequest: {}. Reason: {}", inventoryRequest,
                throwable.getMessage());
        throw new AppException(ErrorCode.DATA_INTEGRITY_VIOLATION_EXCEPTION,
                "Customer Service is unavailable. Please try later.");
    }
}
