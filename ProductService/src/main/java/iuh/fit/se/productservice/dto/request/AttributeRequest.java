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
public class AttributeRequest {
    @NotBlank(message = "NAME_NOT_EMPTY")
    @Size(max = 255, message = "NAME_MAX_LENGTH_255")
    String name;

    @Size(max = 50, message = "TYPE_MAX_LENGTH_50")
    String type;
}
