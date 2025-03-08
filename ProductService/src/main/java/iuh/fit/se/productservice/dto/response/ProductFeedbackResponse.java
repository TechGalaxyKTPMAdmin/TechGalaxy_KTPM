package iuh.fit.se.productservice.dto.response;

import iuh.fit.se.productservice.entities.ProductVariant;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFeedbackResponse {
    private String id;

    private Integer feedbackRating;

    private String feedbackText;

    private String customer_id;

    private ProductVariant productVariant;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
