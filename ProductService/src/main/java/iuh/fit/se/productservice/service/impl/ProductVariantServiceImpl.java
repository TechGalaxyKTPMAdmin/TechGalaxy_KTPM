package iuh.fit.se.productservice.service.impl;


import iuh.fit.se.productservice.dto.request.ProductVariantRequest;
import iuh.fit.se.productservice.dto.response.ProductVariantResponse;
import iuh.fit.se.productservice.entities.Product;
import iuh.fit.se.productservice.entities.ProductVariant;
import iuh.fit.se.productservice.entities.UsageCategory;
import iuh.fit.se.productservice.exception.AppException;
import iuh.fit.se.productservice.exception.ErrorCode;
import iuh.fit.se.productservice.mapper.ProductVariantMapper;
import iuh.fit.se.productservice.repository.ProductRepository;
import iuh.fit.se.productservice.repository.ProductVariantRepository;
import iuh.fit.se.productservice.repository.UsageCategoryRepository;
import iuh.fit.se.productservice.service.ProductVariantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantServiceImpl implements ProductVariantService {
    ProductVariantRepository productVariantRepository;
    ProductRepository productRepository;
    UsageCategoryRepository usageCategoryRepository;
    ProductVariantMapper productVariantMapper;

    @Override
    public Set<ProductVariantResponse> getAllProductVariantsByProductId(String productId) {
        return productVariantRepository.findAllByProductId(productId).stream()
                .map(productVariantMapper::toProductVariantResponse).collect(Collectors.toSet());
    }


    @Override
    public ProductVariantResponse createProductVariant(String productId, ProductVariantRequest request) {
        UsageCategory usageCategory = usageCategoryRepository.findById(request.getUsageCategoryId()).orElseThrow(
                () -> new RuntimeException("Usage category not found")
        );
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("Product not found")
        );
        ProductVariant productVariant = productVariantMapper.toProductVariant(request);
        productVariant.setUsageCategory(usageCategory);
        productVariant.setProduct(product);
        return productVariantMapper.toProductVariantResponse(productVariantRepository.save(productVariant));
    }

    @Override
    public ProductVariantResponse findVariantById(String id) {
        return productVariantRepository.findById(id)
                .map(productVariantMapper::toProductVariantResponse)
                .orElseThrow(() -> new RuntimeException("Product variant not found"));
    }

    @Override
    public ProductVariantResponse updateVariant(String id, ProductVariantRequest request) {
        ProductVariant productVariant = productVariantRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.PRODUCT_NOTFOUND));
        UsageCategory usageCategory = usageCategoryRepository.findById(request.getUsageCategoryId()).orElseThrow(
                () -> new AppException(ErrorCode.USAGE_CATEGORY_NOTFOUND)
        );
        productVariant.setUsageCategory(usageCategory);
        productVariantMapper.updateProductVariantFromRequest(productVariant, request);
        return productVariantMapper.toProductVariantResponse(productVariantRepository.save(productVariant));
    }


    @Override
    public void deleteVariant(String id) {
        ProductVariant productVariant = productVariantRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Product variant not found"));
//        long usageCount = productVariantRepository.countOrderDetailsByProductVariantId(id);
//        if (usageCount > 0) {
//            throw new IllegalStateException("Cannot delete product variant as it is referenced in orders.");
//        }
        productVariantRepository.delete(productVariant);
    }

    @Override
    public ProductVariantResponse findProductVariantByProductVariantDetailId(String id) {
        ProductVariant productVariant = productVariantRepository.findProductVariantByProductVariantDetailId(id);
        return productVariantMapper.toProductVariantResponse(productVariant);
    }

    @Override
    public List<ProductVariantResponse> getAll() {
        return productVariantRepository.findAll().stream()
                .map(productVariantMapper::toProductVariantResponse)
                .collect(Collectors.toList());
    }
}
