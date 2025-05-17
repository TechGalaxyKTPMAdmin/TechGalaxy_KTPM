package iuh.fit.se.productservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class ProductFeedbackRequest {

    String id;

    @NotNull(message = "FEEDBACK_RATING_REQUIRED")
    @Min(value = 1, message = "FEEDBACK_RATING_MIN_1")
    @Max(value = 5, message = "FEEDBACK_RATING_MAX_5")
    Integer feedbackRating;

    @Size(max = 65535, message = "FEEDBACK_TEXT_MAX_LENGTH_EXCEEDED")
    String feedbackText;

    @NotBlank(message = "CUSTOMER_ID_NOT_EMPTY")
    String customerId;

    @NotBlank(message = "PRODUCT_VARIANT_ID_NOT_EMPTY")
    String productVariantId;
}
