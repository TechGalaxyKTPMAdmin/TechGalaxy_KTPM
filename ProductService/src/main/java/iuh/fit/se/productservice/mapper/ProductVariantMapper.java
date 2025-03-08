package iuh.fit.se.productservice.mapper;

import iuh.fit.se.productservice.dto.request.ProductVariantRequest;
import iuh.fit.se.productservice.dto.response.ProductVariantResponse;
import iuh.fit.se.productservice.entities.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {
    ProductVariantResponse toProductVariantResponse(ProductVariant productVariant);

    ProductVariant toProductVariant(ProductVariantRequest productVariantRequest);

    void updateProductVariantFromRequest(@MappingTarget ProductVariant productVariant, ProductVariantRequest productVariantRequest);
}
