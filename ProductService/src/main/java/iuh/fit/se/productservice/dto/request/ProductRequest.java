package iuh.fit.se.productservice.dto.request;

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
public class ProductRequest {

    @NotBlank(message = "PRODUCT_NAME_NOT_EMPTY")
    @Size(min = 5, max = 255, message = "PRODUCT_NAME_INVALID")
    String name;

    @NotBlank(message = "TRADEMARK_ID_NOT_EMPTY")
    String trademarkId;
}
