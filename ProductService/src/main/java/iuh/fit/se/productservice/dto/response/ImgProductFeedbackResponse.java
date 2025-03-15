package iuh.fit.se.productservice.dto.response;

import java.time.LocalDateTime;

import iuh.fit.se.productservice.entities.ProductFeedback;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImgProductFeedbackResponse {
    private String id;

    private ProductFeedback productFeedback;

    private String imagePath;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
