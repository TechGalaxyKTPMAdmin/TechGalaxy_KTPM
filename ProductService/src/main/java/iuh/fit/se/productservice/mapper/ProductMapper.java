package iuh.fit.se.productservice.mapper;

import iuh.fit.se.productservice.dto.request.ProductRequest;
import iuh.fit.se.productservice.dto.response.ProductResponse;
import iuh.fit.se.productservice.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(Product product);

    void updateProductFromRequest(@MappingTarget Product product, ProductRequest productRequest);
}
