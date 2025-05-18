package iuh.fit.se.inventoryservice.client;

import iuh.fit.se.inventoryservice.dto.request.ProductDetailUpdateRequest;
import iuh.fit.se.inventoryservice.dto.response.ProductDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iuh.fit.se.inventoryservice.dto.response.DataResponse;

@FeignClient(name = "ProductService")
public interface ProductClient {
    @PutMapping("/products/variants/details/{productDetailId}")
    DataResponse<Boolean> updateProductVariantDetail(
            @PathVariable("productDetailId") String productDetailId,
            @RequestBody ProductDetailUpdateRequest productDetailUpdateRequest
    );

    @GetMapping("/products/variants/details/{productDetailId}")
    DataResponse<ProductDetailResponse> getProductDetail(@PathVariable String productDetailId) ;

}
