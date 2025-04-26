package iuh.fit.se.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFeedbackRequestV2 {

    @Size(max = 65535, message = "FEEDBACK_TEXT_MAX_LENGTH_EXCEEDED")
    String feedbackText;

    @NotBlank(message = "CUSTOMER_ID_NOT_EMPTY")
    String customerId;

    @NotBlank(message = "PRODUCT_VARIANT_ID_NOT_EMPTY")
    String productVariantId;

    List<String> imagePaths;
}
