package iuh.fit.se.inventoryservice.dto.request;

import iuh.fit.se.inventoryservice.entities.enumeration.ProductStatus;
import iuh.fit.se.inventoryservice.validate.DiscountConstraint;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailUpdateRequest {

    @DecimalMin(value = "0.0", message = "PRODUCT_PRICE_INVALID")
    @NotNull(message = "PRODUCT_PRICE_INVALID")
    Double price;
    @NotNull(message = "PRODUCT_DISCOUNT_INVALID")
    @DiscountConstraint(message = "PRODUCT_DISCOUNT_INVALID")
    Double sale;
    ProductStatus status;
    Integer quantity;
}
