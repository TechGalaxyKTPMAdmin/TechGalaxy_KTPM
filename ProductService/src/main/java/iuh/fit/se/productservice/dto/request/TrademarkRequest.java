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
public class TrademarkRequest {

    String id;

    @NotBlank(message = "TRADEMARK_NAME_NOT_EMPTY")
    @Size(max = 255, message = "TRADEMARK_NAME_MAX_LENGTH_255")
    String name;
}
