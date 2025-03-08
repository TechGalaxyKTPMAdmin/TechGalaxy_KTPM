package iuh.fit.se.productservice.service;

import iuh.fit.se.techgalaxy.dto.request.ProductsImageRequest;
import iuh.fit.se.techgalaxy.dto.response.ProductsImageResponse;

import java.util.List;

public interface ProductsImageService {

    List<ProductsImageResponse> createProductsImage(String variantDetail, List<ProductsImageRequest> productsImageRequest);

    List<ProductsImageResponse> getProductsImageByProductId(String productId);

    List<ProductsImageResponse> getProductsImageByProductVariantId(String productVariantId);

    void deleteProductsImageByProductId(String productId);

    void deleteProductsImageByImageId(String imageId);
}
