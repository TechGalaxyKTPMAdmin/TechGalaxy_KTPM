package iuh.fit.se.productservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductPageResponse {
    String id;
    String variantId;
    Double price;
    Double sale;
    String name;
    String avatar;
}
