package iuh.fit.se.productservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsageCategoryRequest {
    String name;
    String description;
    String avatar;
    String status;
}
