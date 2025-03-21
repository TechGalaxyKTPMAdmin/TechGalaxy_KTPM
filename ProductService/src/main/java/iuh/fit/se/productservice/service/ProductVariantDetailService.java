package iuh.fit.se.productservice.service;


import iuh.fit.se.productservice.dto.request.ProductDetailUpdateRequest;
import iuh.fit.se.productservice.dto.request.ProductVariantDetailRequest;
import iuh.fit.se.productservice.dto.response.ProductDetailResponse;
import iuh.fit.se.productservice.dto.response.ProductPageResponse;
import iuh.fit.se.productservice.dto.response.ProductVariantDetailResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductVariantDetailService {

    ProductVariantDetailResponse getProductVariantDetail(String variantId);

    ProductDetailResponse getProductDetail(String productDetailId);

    List<ProductDetailResponse> getProductDetailsByIds(List<String> productDetailIds);

    List<String> createProductVariantDetail(String variantId, List<ProductVariantDetailRequest> productVariantDetailRequest);

    ProductDetailResponse updateProductVariantDetail(String productDetailId, ProductDetailUpdateRequest productDetailUpdateRequest);

    void deleteProductVariantDetail(String productDetailId);

    Page<ProductPageResponse> getFilteredProductDetails(List<String> trademark,
                                                        Double minPrice, Double maxPrice,
                                                        List<String> memory,
                                                        List<String> usageCategoryId,
                                                        List<String> values,
                                                        String sort,
                                                        Integer page,
                                                        Integer size);

    ProductDetailResponse findProductVariantDetailByProductVariantAndColorAndMemory(String productVariantId, String color, String memory);

    ProductDetailResponse updateQuantity(String productVariantDetailId, int quantity);
}
