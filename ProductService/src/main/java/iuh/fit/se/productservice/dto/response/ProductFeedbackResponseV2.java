package iuh.fit.se.productservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFeedbackResponseV2 {
     String id;
     String feedbackText;
     List<String> imgFeedbacks;
     String customerAvatar;
     String customerName;
     LocalDateTime createdAt;
     LocalDateTime updatedAt;
}
