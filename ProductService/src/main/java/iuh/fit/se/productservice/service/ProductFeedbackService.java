package iuh.fit.se.productservice.service;

import iuh.fit.se.productservice.dto.request.ProductFeedbackRequest;
import iuh.fit.se.productservice.dto.request.ProductFeedbackRequestV2;
import iuh.fit.se.productservice.dto.response.ProductFeedbackResponse;
import iuh.fit.se.productservice.dto.response.ProductFeedbackResponseV2;

import java.util.List;



public interface ProductFeedbackService {
    ProductFeedbackResponse createFeedback(ProductFeedbackRequest productFeedbackRequest);//save

    boolean deleteFeedback(String id); //delete

    ProductFeedbackResponse updateFeedback(String id, Integer newFeedbackRating, String newFeedbackText);// save || update

    List<ProductFeedbackResponse> getAllFeedback(); //findAll

    List<ProductFeedbackResponse> getFeedbackByCustomerId(String customerId);

    List<ProductFeedbackResponse> getFeedbackByProductVariantId(String productVariantId);

    List<ProductFeedbackResponseV2> getFeedbackByProductVariantIdV2(String productVariantId);

    ProductFeedbackResponseV2 createFeedbackV2(ProductFeedbackRequestV2 productFeedbackRequestV2);
}
