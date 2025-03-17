package iuh.fit.se.productservice.controller;


import iuh.fit.se.productservice.dto.request.ProductFeedbackRequest;
import iuh.fit.se.productservice.dto.request.ProductFeedbackRequestV2;
import iuh.fit.se.productservice.dto.response.DataResponse;
import iuh.fit.se.productservice.dto.response.ProductFeedbackResponse;
import iuh.fit.se.productservice.dto.response.ProductFeedbackResponseV2;
import iuh.fit.se.productservice.entities.ProductVariant;
import iuh.fit.se.productservice.service.ProductFeedbackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/product-feedbacks")
public class ProductFeedbackController {
    ProductFeedbackService productFeedbackServiceImpl;


    /**
     * Get all feedbacks
     */
    @GetMapping
    public ResponseEntity<DataResponse<ProductFeedbackResponse>> getAllFeedbacks() {
        List<ProductFeedbackResponse> feedbackResponses = productFeedbackServiceImpl.getAllFeedback();
        return ResponseEntity.ok(DataResponse.<ProductFeedbackResponse>builder()
                .data(feedbackResponses)
                .build());
    }

    /**
     * Create a new feedback
     */
    @PostMapping
    public ResponseEntity<DataResponse<ProductFeedbackResponse>> createFeedback(@RequestBody ProductFeedbackRequest request) {
        ProductFeedbackResponse createdFeedback = productFeedbackServiceImpl.createFeedback(request);
        return ResponseEntity.ok(
                DataResponse.<ProductFeedbackResponse>builder()
                        .data(List.of(createdFeedback))
                        .build()
        );
    }

    @PostMapping("/v2")
    public ResponseEntity<DataResponse<ProductFeedbackResponseV2>> createFeedbackV2(@RequestBody ProductFeedbackRequestV2 request) {
        ProductFeedbackResponseV2 createdFeedback = productFeedbackServiceImpl.createFeedbackV2(request);
        return ResponseEntity.ok(
                DataResponse.<ProductFeedbackResponseV2>builder()
                        .data(List.of(createdFeedback))
                        .build()
        );
    }

    /**
     * Update an existing feedback by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<ProductFeedbackResponse>> updateFeedback(@RequestBody ProductFeedbackRequest productFeedbackRequest) {


        ProductFeedbackResponse productFeedback = productFeedbackServiceImpl.updateFeedback(productFeedbackRequest.getId(), productFeedbackRequest.getFeedbackRating(), productFeedbackRequest.getFeedbackText());
        return ResponseEntity.ok(DataResponse.<ProductFeedbackResponse>builder()
                .data(List.of(productFeedback))
                .build());
    }

    /**
     * Delete feedback by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<ProductFeedbackResponse>> deleteFeedback(@PathVariable String id) {

        boolean isDeleted = productFeedbackServiceImpl.deleteFeedback(id);
        return ResponseEntity.ok(DataResponse.<ProductFeedbackResponse>builder().message("Delete " + id + " success").build());
    }

    /**
     * Get feedbacks by customer ID
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<DataResponse<ProductFeedbackResponse>> getFeedbackByCustomerId(@PathVariable String customerId) {
        List<ProductFeedbackResponse> feedbackResponses = productFeedbackServiceImpl.getFeedbackByCustomerId(customerId);
        return ResponseEntity.ok(
                DataResponse.<ProductFeedbackResponse>builder()
                        .data(feedbackResponses)
                        .build()
        );
    }

    /**
     * Get feedbacks by product variant ID
     */
    @GetMapping("/product-variant/{productVariantId}")
    public ResponseEntity<DataResponse<ProductFeedbackResponse>> getFeedbackByProductVariantId(@PathVariable String productVariantId) {
        List<ProductFeedbackResponse> feedbackResponses = productFeedbackServiceImpl.getFeedbackByProductVariantId(productVariantId);
        
        feedbackResponses.forEach(feedbackResponse -> {
        	ProductVariant productVariant = new ProductVariant();
        	productVariant.setId(feedbackResponse.getProductVariant().getId());
        	
        	feedbackResponse.setProductVariant(productVariant);
        });
  
        return ResponseEntity.ok(
                DataResponse.<ProductFeedbackResponse>builder()
                        .data(feedbackResponses)
                        .build()
        );
    }
    @GetMapping("/product-variantv2/{productVariantId}")
    public ResponseEntity<DataResponse<ProductFeedbackResponseV2>> getFeedbackByProductVariantIdV2(@PathVariable String productVariantId) {
        List<ProductFeedbackResponseV2> feedbackResponses = productFeedbackServiceImpl.getFeedbackByProductVariantIdV2(productVariantId);
        return
                ResponseEntity.ok(DataResponse.<ProductFeedbackResponseV2>builder()
                        .data(feedbackResponses)
                        .build()
        );
    }
}
