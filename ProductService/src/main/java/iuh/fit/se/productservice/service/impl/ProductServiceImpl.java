package iuh.fit.se.productservice.service.impl;

import iuh.fit.se.techgalaxy.dto.request.ProductRequest;
import iuh.fit.se.techgalaxy.dto.response.ProductResponse;
import iuh.fit.se.techgalaxy.entities.Product;
import iuh.fit.se.techgalaxy.entities.Trademark;
import iuh.fit.se.techgalaxy.exception.AppException;
import iuh.fit.se.techgalaxy.exception.ErrorCode;
import iuh.fit.se.techgalaxy.mapper.ProductMapper;
import iuh.fit.se.techgalaxy.repository.ProductRepository;
import iuh.fit.se.techgalaxy.repository.TrademarkRepository;
import iuh.fit.se.techgalaxy.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public Set<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Trademark trademark = trademarkRepository.findById(productRequest.getTrademarkId()).orElseThrow(
                () -> new RuntimeException("Trademark not found")
        );
        Product product = Product.builder().name(productRequest.getName())
                .trademark(trademark)
                .build();
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse getProductById(String id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.updateProductFromRequest(product, productRequest);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(String id) {
        long usageCount = productRepository.countOrderDetailsByProductId(id);

        if (usageCount > 0) {
            throw new IllegalStateException("Cannot delete product as it is referenced in orders.");
        }
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
}
