package iuh.fit.se.productservice.service;

import iuh.fit.se.techgalaxy.dto.request.ProductRequest;
import iuh.fit.se.techgalaxy.dto.response.ProductResponse;

import java.util.Set;

public interface ProductService {

    Set<ProductResponse> getAllProducts();

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse getProductById(String id);

    ProductResponse updateProduct(String id, ProductRequest productRequest);

    void deleteProduct(String id);
}

