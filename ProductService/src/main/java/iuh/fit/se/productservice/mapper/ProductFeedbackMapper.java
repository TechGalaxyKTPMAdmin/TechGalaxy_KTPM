package iuh.fit.se.productservice.mapper;

import iuh.fit.se.productservice.dto.request.ProductFeedbackRequest;
import iuh.fit.se.productservice.dto.request.ProductFeedbackRequestV2;
import iuh.fit.se.productservice.dto.response.ProductFeedbackResponse;
import iuh.fit.se.productservice.dto.response.ProductFeedbackResponseV2;
import iuh.fit.se.productservice.entities.ImgProductFeedback;
import iuh.fit.se.productservice.entities.ProductFeedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public interface ProductFeedbackMapper {
    ProductFeedbackResponse toResponsedto(ProductFeedback productFeedback);

    ProductFeedback toEntity(ProductFeedbackRequest feedbackRequest);
    ProductFeedback toEntityV2(ProductFeedbackRequestV2 feedbackRequest);
    @Mapping(source = "productFeedback.id", target = "id")
    @Mapping(source = "productFeedback.feedbackText", target = "feedbackText")
    @Mapping(source = "productFeedback.customer.name", target = "customerName")
    @Mapping(source = "productFeedback.customer.avatar", target = "customerAvatar")
    @Mapping(source = "productFeedback.createdAt", target = "createdAt")
    @Mapping(source = "productFeedback.updatedAt", target = "updatedAt")
    @Mapping(target = "imgFeedbacks", expression = "java(mapImagePaths(productFeedback.getImgProductFeedbacks()))")
    ProductFeedbackResponseV2 toProductFeedbackResponseV2(ProductFeedback productFeedback);

    default List<String> mapImagePaths(List<ImgProductFeedback> imgProductFeedbacks) {
        if (imgProductFeedbacks == null) {
            return null;
        }
        return imgProductFeedbacks.stream()
                .map(ImgProductFeedback::getImagePath)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

} 
