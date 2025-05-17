package iuh.fit.se.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsageCategoryRequest {

    @NotBlank(message = "USAGE_CATEGORY_NAME_NOT_EMPTY")
    @Size(max = 255, message = "USAGE_CATEGORY_NAME_MAX_LENGTH_255")
    String name;

    @Size(max = 65535, message = "USAGE_CATEGORY_DESCRIPTION_MAX_LENGTH_EXCEEDED")
    String description;

    @Size(max = 500, message = "USAGE_CATEGORY_AVATAR_MAX_LENGTH_500")
    String avatar;

    @Size(max = 50, message = "USAGE_CATEGORY_STATUS_MAX_LENGTH_50")
    String status;
}
