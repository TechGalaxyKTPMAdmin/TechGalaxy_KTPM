package iuh.fit.se.productservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import iuh.fit.se.productservice.dto.request.ProductRequest;
import iuh.fit.se.productservice.dto.response.ProductResponse;
import iuh.fit.se.productservice.entities.Product;
import iuh.fit.se.productservice.entities.Trademark;
import iuh.fit.se.productservice.exception.AppException;
import iuh.fit.se.productservice.exception.ErrorCode;
import iuh.fit.se.productservice.mapper.ProductMapper;
import iuh.fit.se.productservice.repository.ProductRepository;
import iuh.fit.se.productservice.repository.TrademarkRepository;
import iuh.fit.se.productservice.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    TrademarkRepository trademarkRepository;
    ProductMapper productMapper;
    @Qualifier("redisObjectMapper")
    ObjectMapper objectMapper;

    @Override
    @Cacheable(value = "ProductResponses", key = "'getAllProducts'", unless = "#result.isEmpty()")
    public Set<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Trademark trademark = trademarkRepository.findById(productRequest.getTrademarkId()).orElseThrow(
                () -> new RuntimeException("Trademark not found"));
        Product product = Product.builder().name(productRequest.getName())
                .trademark(trademark)
                .build();
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    @Cacheable(value = "ProductResponses", key = "#id")
    public ProductResponse getProductById(String id) {
        return objectMapper.convertValue(productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND)), ProductResponse.class);
    }

    @Override
    @CacheEvict(value = "ProductResponses", key = "#id")
    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.updateProductFromRequest(product, productRequest);

        updateFindAllCache();
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @CachePut(value = "ProductResponses", key = "'getAllProducts'")
    public List<ProductResponse> updateFindAllCache() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(String id) {
        // long usageCount = productRepository.countOrderDetailsByProductId(id);
        //
        // if (usageCount > 0) {
        // throw new IllegalStateException("Cannot delete product as it is referenced in
        // orders.");
        // }
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    @CacheEvict(value = "ProductResponses", allEntries = true)
    public void clearCache() {}
}
