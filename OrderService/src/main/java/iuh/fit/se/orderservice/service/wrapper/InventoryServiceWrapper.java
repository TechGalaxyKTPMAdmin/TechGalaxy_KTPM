package iuh.fit.se.orderservice.service.wrapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import iuh.fit.se.orderservice.client.InventoryClient;
import iuh.fit.se.orderservice.exception.AppException;
import iuh.fit.se.orderservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceWrapper {

    private final InventoryClient inventoryClient;

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "checkStockFallback")
    @Retry(name = "inventoryService")
    public Boolean checkStock(String productVariantDetailId, int quantity) {
        log.info("🔍 Checking stock for productVariantDetailId: {}", productVariantDetailId);
        return inventoryClient.checkStock(productVariantDetailId, quantity);
    }


    public Boolean checkStockFallback(String productVariantDetailId, int quantity, Throwable throwable) {
        log.error("❌ InventoryService failed for productVariantDetailId: {}. Reason: {}", productVariantDetailId, throwable.getMessage());
        return false;
    }
}
