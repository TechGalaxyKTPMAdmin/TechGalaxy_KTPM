package iuh.fit.se.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImgProductFeedbackRequest {

    private String id;

    @NotBlank(message = "PRODUCT_FEEDBACK_ID_NOT_EMPTY")
    private String productFeedbackId;

    @NotBlank(message = "IMAGE_PATH_NOT_EMPTY")
    @Size(max = 255, message = "IMAGE_PATH_MAX_LENGTH_255")
    private String imagePath;

    @Size(max = 65535, message = "DESCRIPTION_MAX_LENGTH_EXCEEDED")
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
