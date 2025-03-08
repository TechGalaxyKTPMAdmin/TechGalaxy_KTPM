package iuh.fit.se.productservice.service;

import iuh.fit.se.techgalaxy.dto.request.ProductVariantRequest;
import iuh.fit.se.techgalaxy.dto.response.ProductVariantResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface ProductVariantService {
    Set<ProductVariantResponse> getAllProductVariantsByProductId(String productId);

    ProductVariantResponse createProductVariant(String productId, ProductVariantRequest request);

    ProductVariantResponse findVariantById(String id);

    ProductVariantResponse updateVariant(String id, ProductVariantRequest request);

    void deleteVariant(String id);

    ProductVariantResponse findProductVariantByProductVariantDetailId(String id);

    List<ProductVariantResponse> getAll();
}
