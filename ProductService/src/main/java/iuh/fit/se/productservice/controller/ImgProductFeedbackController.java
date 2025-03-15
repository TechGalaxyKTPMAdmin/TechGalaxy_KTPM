package iuh.fit.se.productservice.controller;

import iuh.fit.se.productservice.dto.request.ImgProductFeedbackRequest;
import iuh.fit.se.productservice.dto.response.DataResponse;
import iuh.fit.se.productservice.dto.response.ImgProductFeedbackResponse;
import iuh.fit.se.productservice.entities.ImgProductFeedback;
import iuh.fit.se.productservice.entities.ProductFeedback;
import iuh.fit.se.productservice.service.impl.ImgProductFeedbackServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/product-feedbacks/{productFeedbackId}/img-feedbacks")
public class ImgProductFeedbackController {

	ImgProductFeedbackServiceImpl imgProductFeedbackServiceImpl;

	@GetMapping
	public ResponseEntity<DataResponse<ImgProductFeedback>> getImgFeedbackPath(@PathVariable String productFeedbackId) {
	    List<ImgProductFeedback> imgFeedbackPaths = imgProductFeedbackServiceImpl.getImgPath(productFeedbackId);

		imgFeedbackPaths.forEach(imgProductFeedback -> {
			ProductFeedback productFeedback = new ProductFeedback();
			productFeedback.setId(productFeedbackId);
			imgProductFeedback.setProductFeedback(productFeedback);
		});
		
	    return ResponseEntity.ok(
	        DataResponse.<ImgProductFeedback>builder()
	            .data(imgFeedbackPaths) 
	            .build());
	}
	
    @PostMapping
    public ResponseEntity<DataResponse<ImgProductFeedbackResponse>> createImgFeedback(@PathVariable String productFeedbackId, @RequestBody ImgProductFeedbackRequest request) {
    	 Set<ImgProductFeedbackResponse> createdImgFeedback = new HashSet<>();
    	 createdImgFeedback.add(imgProductFeedbackServiceImpl.createImgFeedback(productFeedbackId, request));
         return ResponseEntity.ok(DataResponse.<ImgProductFeedbackResponse>builder().data(createdImgFeedback).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<ImgProductFeedbackResponse>> updateImgFeedback(@RequestBody ImgProductFeedbackRequest imgProductFeedbackRequest){
   		ImgProductFeedbackResponse imgproductFeedback = imgProductFeedbackServiceImpl.updateImgFeedback(imgProductFeedbackRequest.getId(),imgProductFeedbackRequest.getImagePath());
		return ResponseEntity.ok(DataResponse.<ImgProductFeedbackResponse>builder()
                .data(List.of(imgproductFeedback))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<ImgProductFeedbackResponse>> deleteImgFeedback(@PathVariable String id) {
    	   boolean isDeleted = imgProductFeedbackServiceImpl.deleteImgFeedback(id);
           return ResponseEntity.ok(DataResponse.<ImgProductFeedbackResponse>builder().message("Delete " + id + " success").build());
    }
}
