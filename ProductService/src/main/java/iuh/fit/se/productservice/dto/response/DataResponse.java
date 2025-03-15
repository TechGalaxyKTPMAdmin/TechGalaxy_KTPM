package iuh.fit.se.productservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import iuh.fit.se.productservice.entities.ImgProductFeedback;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataResponse<T> {
    @Builder.Default
    int status = 200;
    String message;
    Collection<T> data;
}
