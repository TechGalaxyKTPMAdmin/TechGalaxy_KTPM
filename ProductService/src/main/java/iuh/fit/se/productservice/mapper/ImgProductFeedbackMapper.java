package iuh.fit.se.productservice.mapper;

import iuh.fit.se.productservice.dto.request.ImgProductFeedbackRequest;
import iuh.fit.se.productservice.dto.response.ImgProductFeedbackResponse;
import iuh.fit.se.productservice.entities.ImgProductFeedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImgProductFeedbackMapper {
    ImgProductFeedbackResponse toRespondedto(ImgProductFeedback imgProductFeedback);

    iuh.fit.se.productservice.entities.ImgProductFeedback toEntity(ImgProductFeedbackRequest imgFeedbackRequest);
}
