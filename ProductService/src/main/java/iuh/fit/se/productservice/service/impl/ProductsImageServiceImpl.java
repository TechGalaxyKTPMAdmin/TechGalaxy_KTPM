package iuh.fit.se.productservice.service.impl;

import iuh.fit.se.productservice.dto.request.ProductsImageRequest;
import iuh.fit.se.productservice.dto.response.ProductsImageResponse;
import iuh.fit.se.productservice.entities.ProductVariantDetail;
import iuh.fit.se.productservice.entities.ProductsImage;
import iuh.fit.se.productservice.exception.AppException;
import iuh.fit.se.productservice.exception.ErrorCode;
import iuh.fit.se.productservice.mapper.ProductsImageMapper;
import iuh.fit.se.productservice.repository.ProductVariantDetailRepository;
import iuh.fit.se.productservice.repository.ProductsImageRepository;
import iuh.fit.se.productservice.service.ProductsImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductsImageServiceImpl implements ProductsImageService {
    ProductsImageMapper productsImageMapper;
    ProductsImageRepository productsImageRepository;
    ProductVariantDetailRepository productVariantDetailRepository;


    @Override
    public List<ProductsImageResponse> createProductsImage(String variantDetail, List<ProductsImageRequest> productsImageRequest) {
        ProductVariantDetail productVariantDetail = productVariantDetailRepository.findById(variantDetail).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOTFOUND)
        );
        List<ProductsImage> productsImages = productsImageRequest.stream()
                .map(productsImageMapper::toProdcutsImage)
                .peek(productsImage -> productsImage.setProductVariantDetail(productVariantDetail))
                .collect(Collectors.toList());
        return productsImageRepository.saveAll(productsImages).stream().map(productsImageMapper::toProductsImageResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductsImageResponse> getProductsImageByProductId(String productId) {
        List<ProductsImage> productsImages = productsImageRepository.findAllByProductVariantDetailId(productId);
        return productsImages.stream().map(productsImageMapper::toProductsImageResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductsImageResponse> getProductsImageByProductVariantId(String productVariantId) {
        List<ProductsImage> productsImages = productsImageRepository.findAllByProductVariantId(productVariantId);
        return productsImages.stream().map(productsImageMapper::toProductsImageResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteProductsImageByProductId(String productId) {
        List<ProductsImage> productsImages = productsImageRepository.findAllByProductVariantDetailId(productId);
        productsImageRepository.deleteAll(productsImages);
    }

    @Override
    public void deleteProductsImageByImageId(String imageId) {
        ProductsImage productsImage = productsImageRepository.findById(imageId).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOTFOUND)
        );
        productsImageRepository.delete(productsImage);
    }
}
