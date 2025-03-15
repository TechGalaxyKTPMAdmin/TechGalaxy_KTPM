package iuh.fit.se.productservice.dto.request;

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
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImgProductFeedbackRequest {
	
    private String id;

    private String productFeedbackId;

    private String imagePath;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
