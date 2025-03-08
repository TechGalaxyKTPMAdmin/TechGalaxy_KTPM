package iuh.fit.se.productservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFeedbackRequest {
     String id;

     Integer feedbackRating;

     String feedbackText;

     String customerId;

     String productVariantId;
}
