package iuh.fit.se.productservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import iuh.fit.se.productservice.dto.request.InventoryRequest;
import iuh.fit.se.productservice.dto.request.ProductDetailUpdateRequest;
import iuh.fit.se.productservice.dto.request.ProductVariantDetailRequest;
import iuh.fit.se.productservice.dto.response.ProductDetailResponse;
import iuh.fit.se.productservice.dto.response.ProductPageResponse;
import iuh.fit.se.productservice.dto.response.ProductVariantDetailResponse;
import iuh.fit.se.productservice.entities.Color;
import iuh.fit.se.productservice.entities.Memory;
import iuh.fit.se.productservice.entities.ProductVariant;
import iuh.fit.se.productservice.entities.ProductVariantDetail;
import iuh.fit.se.productservice.exception.AppException;
import iuh.fit.se.productservice.exception.ErrorCode;
import iuh.fit.se.productservice.mapper.ProductVariantDetailMapper;
import iuh.fit.se.productservice.repository.ColorRepository;
import iuh.fit.se.productservice.repository.MemoryRepository;
import iuh.fit.se.productservice.repository.ProductVariantDetailRepository;
import iuh.fit.se.productservice.repository.ProductVariantRepository;
import iuh.fit.se.productservice.service.ProductVariantDetailService;
import iuh.fit.se.productservice.service.wrapper.InventoryServiceWrapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantDetailServiceImpl implements ProductVariantDetailService {
    ProductVariantDetailRepository productVariantDetailRepository;
    ProductVariantRepository productVariantRepository;
    ProductVariantDetailMapper productVariantDetailMapper;
    ColorRepository colorRepository;
    MemoryRepository memoryRepository;
    ProductCacheService productCacheService;
    @Qualifier("redisObjectMapper")
    ObjectMapper objectMapper;

    InventoryServiceWrapper inventoryServiceWrapper;

    @Override
    public ProductVariantDetailResponse getProductVariantDetail(String variantId) {
        List<ProductVariantDetail> details = productVariantDetailRepository.findAllByProductVariantId(variantId);
        if (details.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOTFOUND);
        }
        return productVariantDetailMapper.toProductVariantDetailResponse(details.get(0), details);
    }

    @Override
    @Cacheable(value = "ProductDetailResponses", key = "#productDetailId", unless = "#result == null")
    public ProductDetailResponse getProductDetail(String productDetailId) {
        ProductVariantDetail productVariantDetail = productVariantDetailRepository.findById(productDetailId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));
        return objectMapper.convertValue(productVariantDetailMapper.toResponse(productVariantDetail),
                ProductDetailResponse.class);
    }

    @Override
    @Cacheable(value = "ProductDetailResponses", key = "'getProductDetailsByIds'", unless = "#result.isEmpty()")
    public List<ProductDetailResponse> getProductDetailsByIds(List<String> productDetailIds) {
        List<ProductVariantDetail> productVariantDetails = productVariantDetailRepository
                .findAllByIdIsIn(productDetailIds);
        if (!productVariantDetails.isEmpty()) {
            return productVariantDetails.stream().map(productVariantDetailMapper::toResponse).toList();
        }
        return null;
    }

    @Override
    @CacheEvict(value = "ProductDetailResponses", key = "#productDetailId")
    public ProductDetailResponse updateProductVariantDetail(String productDetailId,
            ProductDetailUpdateRequest productDetailUpdateRequest) {
        ProductDetailResponse productDetailResponse;
        ProductVariantDetail productVariantDetail = productVariantDetailRepository.findById(productDetailId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));
        productVariantDetailMapper.toUpdate(productVariantDetail, productDetailUpdateRequest);
        try {
            productDetailResponse = productVariantDetailMapper
                    .toResponse(productVariantDetailRepository.save(productVariantDetail));
        } catch (Exception e) {
            throw new AppException(ErrorCode.PRODUCT_UPDATE_FAILED);
        }
        productCacheService.updateFindAllDetailCache();
        return productDetailResponse;
    }

    @Override
    @CacheEvict(value = "ProductDetailResponses", key = "#productVariantDetailId")
    public ProductDetailResponse updateQuantity(String productVariantDetailId, int quantity) {
        ProductDetailResponse productDetailResponse;
        ProductVariantDetail productVariantDetail = productVariantDetailRepository.findById(productVariantDetailId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));
        if (productVariantDetail.getQuantity() >= quantity) {
            productVariantDetail.setQuantity(productVariantDetail.getQuantity() - quantity);
        } else {
            throw new AppException(ErrorCode.PRODUCT_NOT_ENOUGH);
        }
        try {
            productDetailResponse = productVariantDetailMapper
                    .toResponse(productVariantDetailRepository.save(productVariantDetail));
        } catch (Exception e) {
            throw new AppException(ErrorCode.PRODUCT_UPDATE_FAILED);
        }
        productCacheService.updateFindAllDetailCache();
        return productDetailResponse;
    }

    @Override
    public List<String> createProductVariantDetail(String variantId,
            List<ProductVariantDetailRequest> productVariantDetailRequests) {
        ProductVariant productVariant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));
        List<ProductVariantDetail> detailsToSave = new ArrayList<>();
        for (ProductVariantDetailRequest requestDTO : productVariantDetailRequests) {
            Memory memory = memoryRepository.findById(requestDTO.getMemid())
                    .orElseThrow(() -> new AppException(ErrorCode.MEMORY_NOTFOUND));

            for (ProductVariantDetailRequest.ColorRequest colorRequest : requestDTO.getColors()) {
                Color color = colorRepository.findById(colorRequest.getColorId())
                        .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOTFOUND));
                ProductVariantDetail detail = productVariantDetailMapper.toProductVariantDetail(requestDTO,
                        colorRequest, color, memory, productVariant);
                detailsToSave.add(detail);
            }
        }
        // productVariantDetailRepository.saveAll(detailsToSave);
        Iterable<ProductVariantDetail> savedDetails = productVariantDetailRepository.saveAll(detailsToSave);

        if (savedDetails.iterator().hasNext()) {
            savedDetails.forEach(detail -> {
                InventoryRequest inventoryRequest = new InventoryRequest();
                inventoryRequest.setProductVariantDetailId(detail.getId());
                inventoryRequest.setStockQuantity(detail.getQuantity());
                inventoryServiceWrapper.saveInventory(inventoryRequest);
            });
        }

        List<String> detailIds = new ArrayList<>();
        if (savedDetails.iterator().hasNext()) {
            savedDetails.forEach(detail -> detailIds.add(detail.getId()));
            return detailIds;
        }
        return detailIds;
    }

    @Override
    public void deleteProductVariantDetail(String productDetailId) {
        try {
            // long usageCount =
            // productVariantDetailRepository.countOrderDetailsByProductVariantDetailId(productDetailId);
            // if (usageCount > 0) {
            // throw new AppException(ErrorCode.PRODUCT_DELETE_FAILED);
            // }
            productVariantDetailRepository.deleteById(productDetailId);
        } catch (Exception e) {
            throw new AppException(ErrorCode.PRODUCT_DELETE_FAILED);
        }
    }

    @Override
    public Page<ProductPageResponse> getFilteredProductDetails(
            List<String> trademark,
            Double minPrice,
            Double maxPrice,
            List<String> memory,
            List<String> usageCategoryId,
            List<String> values,
            String sort,
            Integer page,
            Integer size) {

        Pageable pageable;
        if (sort != null && !sort.isEmpty()) {
            Sort sortOrder = sort.equalsIgnoreCase("asc") ? Sort.by("price").ascending()
                    : Sort.by("price").descending();
            pageable = PageRequest.of(page, size, sortOrder);
        } else {
            pageable = PageRequest.of(page, size);
        }

        if (trademark == null)
            trademark = Collections.emptyList();
        if (memory == null)
            memory = Collections.emptyList();
        if (usageCategoryId == null)
            usageCategoryId = Collections.emptyList();
        if (values == null)
            values = Collections.emptyList();

        log.info("Trademark: {}", trademark);
        log.info("Memory: {}", memory);
        log.info("UsageCategoryId: {}", usageCategoryId);
        log.info("Values: {}", values);
        log.info("MinPrice: {}, MaxPrice: {}", minPrice, maxPrice);

        try {
            Page<ProductVariantDetail> productVariantDetails = productVariantDetailRepository
                    .findFilteredProductDetails(trademark, minPrice, maxPrice, memory, usageCategoryId, values,
                            pageable);

            return productVariantDetails.map(productVariantDetailMapper::toResponsePage);
        } catch (Exception e) {
            log.error("Lỗi khi lọc sản phẩm:", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi lọc sản phẩm");
        }
    }

    @Override
    public ProductDetailResponse findProductVariantDetailByProductVariantAndColorAndMemory(String productVariantId,
            String color, String memory) {
        ProductVariantDetail productVariantDetail = productVariantDetailRepository
                .findProductVariantDetailByProductVariantAndColorAndMemory(productVariantId, color, memory);
        return productVariantDetailMapper.toResponse(productVariantDetail);
    }

}
