package iuh.fit.se.productservice.service.impl;

import iuh.fit.se.productservice.dto.response.ProductDetailResponse;
import iuh.fit.se.productservice.dto.response.ProductResponse;
import iuh.fit.se.productservice.entities.Product;
import iuh.fit.se.productservice.entities.ProductVariantDetail;
import iuh.fit.se.productservice.mapper.ProductMapper;
import iuh.fit.se.productservice.mapper.ProductVariantDetailMapper;
import iuh.fit.se.productservice.repository.ProductRepository;
import iuh.fit.se.productservice.repository.ProductVariantDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCacheService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductVariantDetailRepository productVariantDetailRepository;
    private final ProductVariantDetailMapper productVariantDetailMapper;

    @Autowired
    public ProductCacheService(ProductRepository productRepository, ProductMapper productMapper,
                               ProductVariantDetailRepository productVariantDetailRepository,
                               ProductVariantDetailMapper productVariantDetailMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productVariantDetailRepository = productVariantDetailRepository;
        this.productVariantDetailMapper = productVariantDetailMapper;
    }

        @CachePut(value = "ProductResponses", key = "'getAllProducts'")
        public List<ProductResponse> updateFindAllCache () {
            List<Product> products = productRepository.findAll();
            return products.stream()
                    .map(productMapper::toProductResponse)
                    .collect(Collectors.toList());
        }

        @CachePut(value = "ProductDetailResponses", key = "'getProductDetailsByIds'")
        public List<ProductDetailResponse> updateFindAllDetailCache () {
            List<ProductVariantDetail> productVariantDetails = productVariantDetailRepository.findAll();
            return productVariantDetails.stream().map(productVariantDetailMapper::toResponse).toList();
        }
    }
