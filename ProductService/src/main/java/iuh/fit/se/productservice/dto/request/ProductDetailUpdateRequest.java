package iuh.fit.se.productservice.dto.request;

import iuh.fit.se.productservice.entities.enumeration.ProductStatus;
import iuh.fit.se.productservice.validate.DiscountConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailUpdateRequest {
    Double price;
    @DiscountConstraint(message = "PRODUCT_DISCOUNT_INVALID")
    Double sale;
    ProductStatus status;
    Integer quantity;
}
