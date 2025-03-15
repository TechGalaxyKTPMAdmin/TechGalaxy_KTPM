package iuh.fit.se.productservice.service;

import java.util.List;

import iuh.fit.se.productservice.dto.request.ImgProductFeedbackRequest;
import iuh.fit.se.productservice.dto.response.ImgProductFeedbackResponse;
import iuh.fit.se.productservice.entities.ImgProductFeedback;

public interface ImgProductFeedbackService {
    ImgProductFeedbackResponse createImgFeedback(String productFeedbackId, ImgProductFeedbackRequest imgProductFeedbackRequest);

    boolean deleteImgFeedback(String id);

    ImgProductFeedbackResponse updateImgFeedback(String id, String newImgPath);

    List<ImgProductFeedback> getImgPath(String feedbackId);
}
