package iuh.fit.se.productservice.dto.request;

import iuh.fit.se.productservice.entities.enumeration.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantRequest {

    @NotBlank(message = "PRODUCT_VARIANT_NAME_NOT_EMPTY")
    @Size(max = 255, message = "PRODUCT_VARIANT_NAME_MAX_LENGTH_255")
    String name;

    @Size(max = 65535, message = "DESCRIPTION_MAX_LENGTH_EXCEEDED")
    String description;

    @Size(max = 65535, message = "CONTENT_MAX_LENGTH_EXCEEDED")
    String content;

    @Size(max = 255, message = "AVATAR_PATH_MAX_LENGTH_255")
    String avatar;

    Boolean featured;

    @NotNull(message = "PRODUCT_VARIANT_STATUS_NOT_NULL")
    ProductStatus status;

    @NotBlank(message = "USAGE_CATEGORY_ID_NOT_EMPTY")
    String usageCategoryId;
}
