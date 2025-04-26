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
public class ProductsImageRequest {

    @NotBlank(message = "PATH_NOT_EMPTY")
    @Size(max = 255, message = "PATH_MAX_LENGTH_255")
    String path;

    @NotNull(message = "AVATAR_FLAG_REQUIRED")
    Boolean avatar;
}
