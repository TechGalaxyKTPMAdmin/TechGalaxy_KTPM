package iuh.fit.se.productservice.controller;

import iuh.fit.se.productservice.dto.request.ProductDetailUpdateRequest;
import iuh.fit.se.productservice.dto.request.ProductVariantDetailRequest;
import iuh.fit.se.productservice.dto.response.DataResponse;
import iuh.fit.se.productservice.dto.response.ProductDetailResponse;
import iuh.fit.se.productservice.dto.response.ProductPageResponse;
import iuh.fit.se.productservice.dto.response.ProductVariantDetailResponse;
import iuh.fit.se.productservice.service.ProductVariantDetailService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping({ "/products/variants/{variantId}/details", "/products/variants/details" })
@Slf4j
public class ProductVariantDetailController {
    static String successMessage = "success";
    ProductVariantDetailService productVariantDetailServiceImpl;
    PagedResourcesAssembler<ProductPageResponse> pagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<DataResponse<ProductVariantDetailResponse>> getAllProductVariantDetails(
            @PathVariable String variantId) {
        System.out.println(variantId);
        Set<ProductVariantDetailResponse> productVariantDetailResponses = new HashSet<>();
        productVariantDetailResponses.add(productVariantDetailServiceImpl.getProductVariantDetail(variantId));
        return ResponseEntity
                .ok(DataResponse.<ProductVariantDetailResponse>builder().data(productVariantDetailResponses).build());
    }

    @GetMapping("/{productDetailId}")
    public ResponseEntity<DataResponse<ProductDetailResponse>> getProductDetail(@PathVariable String productDetailId) {
        Set<ProductDetailResponse> productDetailResponses = new HashSet<>();
        productDetailResponses.add(productVariantDetailServiceImpl.getProductDetail(productDetailId));
        return ResponseEntity.ok(DataResponse.<ProductDetailResponse>builder().data(productDetailResponses).build());
    }

    @GetMapping("/getProductDetailsByIds")
    public ResponseEntity<DataResponse<ProductDetailResponse>> getAllProductDetailsByIds(
            @RequestParam List<String> productDetailIds) {
        List<ProductDetailResponse> productDetailResponses = productVariantDetailServiceImpl
                .getProductDetailsByIds(productDetailIds);
        return ResponseEntity.ok(DataResponse.<ProductDetailResponse>builder().data(productDetailResponses).build());
    }

    @PostMapping
    public ResponseEntity<DataResponse<String>> createProductVariantDetail(@PathVariable String variantId,
            @RequestBody List<ProductVariantDetailRequest> productVariantDetailRequest) {
        List<String> detailIds = productVariantDetailServiceImpl.createProductVariantDetail(variantId,
                productVariantDetailRequest);
        return ResponseEntity.ok(DataResponse.<String>builder().data(detailIds).message(successMessage).build());
    }

    @PutMapping("/{productDetailId}")
    public ResponseEntity<DataResponse<Boolean>> updateProductVariantDetail(@PathVariable String productDetailId,
            @Valid @RequestBody ProductDetailUpdateRequest productDetailUpdateRequest) {
        productVariantDetailServiceImpl.updateProductVariantDetail(productDetailId, productDetailUpdateRequest);
        return ResponseEntity.ok(DataResponse.<Boolean>builder().message(successMessage).build());
    }

    @DeleteMapping("/{productDetailId}")
    public ResponseEntity<DataResponse<Boolean>> deleteProductVariantDetail(@PathVariable String productDetailId) {
        productVariantDetailServiceImpl.deleteProductVariantDetail(productDetailId);
        return ResponseEntity.ok(DataResponse.<Boolean>builder().message(successMessage).build());
    }

    @GetMapping("/filter")
    public ResponseEntity<PagedModel<EntityModel<ProductPageResponse>>> getFilteredProductDetails(
            @RequestParam(required = false) List<String> trademark,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) List<String> memory,
            @RequestParam(required = false) List<String> usageCategoryId,
            @RequestParam(required = false) List<String> values,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("getFilteredProductDetails");
        Page<ProductPageResponse> response = productVariantDetailServiceImpl.getFilteredProductDetails(
                trademark, minPrice, maxPrice, memory, usageCategoryId, values, sort, page, size);
        PagedModel<EntityModel<ProductPageResponse>> pagedModel = pagedResourcesAssembler.toModel(response);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/getProductVariantDetailByProductVariantAndColorAndMemory")
    public ResponseEntity<DataResponse<ProductDetailResponse>> findProductVariantDetailByProductVariantAndColorAndMemory(
            @RequestParam String productVariantId, @RequestParam String color, @RequestParam String memory) {
        List<ProductDetailResponse> productDetailResponses = List.of(productVariantDetailServiceImpl
                .findProductVariantDetailByProductVariantAndColorAndMemory(productVariantId, color, memory));
        return ResponseEntity.ok(DataResponse.<ProductDetailResponse>builder().data(productDetailResponses).build());
    }
}
