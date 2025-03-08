package iuh.fit.se.productservice.dto.request;

import iuh.fit.se.productservice.entities.enumeration.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantRequest {
    String name;
    String description;
    String content;
    String avatar;
    Boolean featured;
    ProductStatus status;
    String usageCategoryId;
}
