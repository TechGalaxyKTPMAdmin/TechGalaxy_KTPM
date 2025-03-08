package iuh.fit.se.productservice.mapper;

import iuh.fit.se.productservice.dto.request.ProductsImageRequest;
import iuh.fit.se.productservice.dto.response.ProductsImageResponse;
import iuh.fit.se.productservice.entities.ProductsImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductsImageMapper {

    ProductsImage toProdcutsImage(ProductsImageRequest productsImageRequest);


    ProductsImageResponse toProductsImageResponse(ProductsImage productsImage);
}
