package iuh.fit.se.productservice.dto.response;

import iuh.fit.se.productservice.entities.Color;
import iuh.fit.se.productservice.entities.Memory;
import iuh.fit.se.productservice.entities.ProductsImage;
import iuh.fit.se.productservice.entities.enumeration.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse {
    String id;
    String productVariantId;
    Color color;
    Memory memory;
    Integer viewsCount = 0;
    Integer quantity;
    Double price;
    Double sale;
    ProductStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<ProductsImage> productsImage;
}
