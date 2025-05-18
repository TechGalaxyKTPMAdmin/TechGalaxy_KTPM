package iuh.fit.se.inventoryservice.wrapper;

import iuh.fit.se.inventoryservice.client.ProductClient;
import iuh.fit.se.inventoryservice.dto.request.ProductDetailUpdateRequest;
import iuh.fit.se.inventoryservice.dto.response.DataResponse;
import iuh.fit.se.inventoryservice.dto.response.ProductDetailResponse;
import iuh.fit.se.inventoryservice.exception.AppException;
import iuh.fit.se.inventoryservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceWrapper {

    private final ProductClient productClient;

    @CircuitBreaker(name = "productService", fallbackMethod = "updateProductDetailFallback")
    @Retry(name = "productService")
    public Collection<Boolean> updateProductDetail(String productDetailId, ProductDetailUpdateRequest request) {
        log.info("Calling ProductService to update product detail: {}", productDetailId);
        DataResponse<Boolean> response = productClient.updateProductVariantDetail(productDetailId, request);
        return response.getData();

    }

    @CircuitBreaker(name = "productService", fallbackMethod = "getProductDetail")
    @Retry(name = "productService")
    public Collection<ProductDetailResponse> getProductDetail(String productDetailId) {
        log.info("Calling ProductService to get product detail: {}", productDetailId);
        DataResponse<ProductDetailResponse> response = productClient.getProductDetail(productDetailId);
        return response.getData();
    }

    public Collection<Boolean> getProductDetail(
            String productDetailId, Throwable throwable) {
        log.error("❌ ProductService failed for get Product Detail id: {}. Reason: {}", productDetailId,
                throwable.getMessage());
        throw new AppException(ErrorCode.DATA_INTEGRITY_VIOLATION_EXCEPTION,
                "Customer Service is unavailable. Please try later.");
    }

    public Collection<Boolean> updateProductDetailFallback(
            String productDetailId, ProductDetailUpdateRequest request, Throwable throwable) {
        log.error("❌ ProductService failed for update Product Detail id: {}, request: {}. Reason: {}", productDetailId, request,
                throwable.getMessage());
        throw new AppException(ErrorCode.DATA_INTEGRITY_VIOLATION_EXCEPTION,
                "Customer Service is unavailable. Please try later.");
    }
}
