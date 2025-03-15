package iuh.fit.se.productservice.controller;

import iuh.fit.se.productservice.dto.request.ProductVariantRequest;
import iuh.fit.se.productservice.dto.response.DataResponse;
import iuh.fit.se.productservice.dto.response.ProductVariantResponse;
import iuh.fit.se.productservice.service.ProductVariantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping({"/products/{productId}/variants", "/products/variants"})
public class ProductVariantController {
    ProductVariantService productVariantServiceImpl;

    @GetMapping
    public ResponseEntity<DataResponse<ProductVariantResponse>> getAllProductVariants(@PathVariable String productId) {
        return ResponseEntity.ok(DataResponse.<ProductVariantResponse>builder().data(productVariantServiceImpl.getAllProductVariantsByProductId(productId)).build());
    }

    @PostMapping
    public ResponseEntity<DataResponse<ProductVariantResponse>> createVariant(@PathVariable String productId, @RequestBody ProductVariantRequest request) {
        Set<ProductVariantResponse> createdVariant = new HashSet<>();
        createdVariant.add(productVariantServiceImpl.createProductVariant(productId, request));
        return ResponseEntity.ok(DataResponse.<ProductVariantResponse>builder().data(createdVariant).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<ProductVariantResponse>> getVariantById(@PathVariable String id) {
        Set<ProductVariantResponse> createdVariant = new HashSet<>();
        createdVariant.add(productVariantServiceImpl.findVariantById(id));
        return ResponseEntity.ok(DataResponse.<ProductVariantResponse>builder().data(createdVariant).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<ProductVariantResponse>> updateVariant(@PathVariable String id, @RequestBody ProductVariantRequest request) {
        Set<ProductVariantResponse> createdVariant = new HashSet<>();
        createdVariant.add(productVariantServiceImpl.updateVariant(id, request));
        return ResponseEntity.ok(DataResponse.<ProductVariantResponse>builder().data(createdVariant).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<Object>> deleteVariant(@PathVariable String id) {
        productVariantServiceImpl.deleteVariant(id);
        return ResponseEntity.ok(DataResponse.<Object>builder().message("Delete " + id + " success").build());
    }

    @GetMapping("/all")
    public ResponseEntity<DataResponse<ProductVariantResponse>> getAll() {
        return ResponseEntity.ok(DataResponse.<ProductVariantResponse>builder().data(productVariantServiceImpl.getAll()).build());
    }

    @GetMapping("/productVariantDetail/{id}")
    public ResponseEntity<DataResponse<ProductVariantResponse>> findProductVariantByProductVariantDetailId(@PathVariable String id) {
        Set<ProductVariantResponse> createdVariant = new HashSet<>();
        createdVariant.add(productVariantServiceImpl.findProductVariantByProductVariantDetailId(id));
        return ResponseEntity.ok(DataResponse.<ProductVariantResponse>builder().data(createdVariant).build());
    }
}




