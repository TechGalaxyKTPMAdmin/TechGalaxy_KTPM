package iuh.fit.se.productservice.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
     @Size(min = 5, max = 24, message = "PRODUCT_NAME_INVALID")
     String name;
     String trademarkId;
}
