package iuh.fit.se.productservice.service.impl;

import iuh.fit.se.productservice.dto.request.ImgProductFeedbackRequest;
import iuh.fit.se.productservice.dto.response.ImgProductFeedbackResponse;
import iuh.fit.se.productservice.entities.ImgProductFeedback;
import iuh.fit.se.productservice.entities.ProductFeedback;
import iuh.fit.se.productservice.exception.AppException;
import iuh.fit.se.productservice.exception.ErrorCode;
import iuh.fit.se.productservice.mapper.ImgProductFeedbackMapper;
import iuh.fit.se.productservice.repository.ImgProductFeedbackRepository;
import iuh.fit.se.productservice.repository.ProductFeedbackRepository;
import iuh.fit.se.productservice.service.ImgProductFeedbackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImgProductFeedbackServiceImpl implements ImgProductFeedbackService {

    ImgProductFeedbackRepository imgProductFeedbackRepository;
    ImgProductFeedbackMapper imgProductFeedbackMapper;
    ProductFeedbackRepository productFeedbackRepository;
    
    @Autowired
	public ImgProductFeedbackServiceImpl(ImgProductFeedbackRepository imgProductFeedbackRepository,
			ImgProductFeedbackMapper imgProductFeedbackMapper,
			ProductFeedbackRepository productFeedbackRepository) {
		super();
		this.imgProductFeedbackRepository = imgProductFeedbackRepository;
		this.imgProductFeedbackMapper = imgProductFeedbackMapper;
		this.productFeedbackRepository = productFeedbackRepository;
	}
    
    
    
    @Override

    public ImgProductFeedbackResponse createImgFeedback(String productFeedbackId, ImgProductFeedbackRequest imgProductFeedbackRequest) {
        ImgProductFeedback imgProductFeedback = imgProductFeedbackMapper.toEntity(imgProductFeedbackRequest);
        //

        ImgProductFeedback imgProductFeedbackCreate = imgProductFeedbackRepository.save(imgProductFeedback);


        ImgProductFeedbackResponse imgProductFeedbackResponse = imgProductFeedbackMapper.toRespondedto(imgProductFeedbackCreate);
        //
        return imgProductFeedbackResponse;
    }

    @Override
    public boolean deleteImgFeedback(String id) {
        ImgProductFeedback imgProductFeedack = imgProductFeedbackRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.IMAGE_FEEDBACK_NOTFOUND));
        if (imgProductFeedack != null) {
            imgProductFeedbackRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public ImgProductFeedbackResponse updateImgFeedback(String id, String newImgPath) {
        ImgProductFeedback imgProductFeedack = imgProductFeedbackRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.IMAGE_FEEDBACK_NOTFOUND));
        if (imgProductFeedack != null) {
            imgProductFeedack.setImagePath(newImgPath);

            return imgProductFeedbackMapper.toRespondedto(imgProductFeedbackRepository.save(imgProductFeedack));
        }
        return null;
    }

    @Override
    public List<ImgProductFeedback> getImgPath(String feedbackId) {
        // TODO Auto-generated method stub
    	System.out.println("feedbackId: "+feedbackId);
        ProductFeedback productFeedback = productFeedbackRepository.getById(feedbackId);
        List<ImgProductFeedback> imgsFeedbacks = productFeedback.getImgProductFeedbacks();
        return imgsFeedbacks;


    }




}


