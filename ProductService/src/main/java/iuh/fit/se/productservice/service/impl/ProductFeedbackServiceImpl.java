package iuh.fit.se.productservice.service.impl;


import iuh.fit.se.productservice.SecurityUtil;
import iuh.fit.se.productservice.dto.request.ProductFeedbackRequest;
import iuh.fit.se.productservice.dto.request.ProductFeedbackRequestV2;
import iuh.fit.se.productservice.dto.response.ProductFeedbackResponse;
import iuh.fit.se.productservice.dto.response.ProductFeedbackResponseV2;
import iuh.fit.se.productservice.entities.Customer;
import iuh.fit.se.productservice.entities.ImgProductFeedback;
import iuh.fit.se.productservice.entities.ProductFeedback;
import iuh.fit.se.productservice.entities.ProductVariant;
import iuh.fit.se.productservice.exception.AppException;
import iuh.fit.se.productservice.exception.ErrorCode;
import iuh.fit.se.productservice.mapper.ProductFeedbackMapper;
import iuh.fit.se.productservice.repository.CustomerRepository;
import iuh.fit.se.productservice.repository.ProductFeedbackRepository;
import iuh.fit.se.productservice.repository.ProductVariantRepository;
import iuh.fit.se.productservice.service.ProductFeedbackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductFeedbackServiceImpl implements ProductFeedbackService {
    ProductFeedbackRepository productFeedbackRepository;
    CustomerRepository customerRepository;
    ProductVariantRepository productVariantRepository;
    ProductFeedbackMapper productFeedbackMapper;

    @Override
    public ProductFeedbackResponse createFeedback(ProductFeedbackRequest productFeedbackRequest) {
        // TODO Auto-generated method stub
        //.... xu ly doi

      ProductFeedback productFeedback = productFeedbackMapper.toEntity(productFeedbackRequest);
        //

        ProductFeedback productFeedbackCreate = productFeedbackRepository.save(productFeedback);
        //save kh co id thi se la inssert
        //save ma co id thi se la update

        ProductFeedbackResponse productFeedbackResponse = productFeedbackMapper.toResponsedto(productFeedbackCreate);
        //
        return productFeedbackResponse;
    }

    @Override
    public boolean deleteFeedback(String id) {

        iuh.fit.se.productservice.entities.ProductFeedback productFeedack = productFeedbackRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOTFOUND));
        if (productFeedack != null) {
            productFeedbackRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public ProductFeedbackResponse updateFeedback(String id, Integer newFeedbackRating, String newFeedbackText) {
        iuh.fit.se.productservice.entities.ProductFeedback productFeedack = productFeedbackRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOTFOUND));
        if (productFeedack != null) {
            productFeedack.setFeedbackRating(null);
            productFeedack.setFeedbackText(newFeedbackText);

            return productFeedbackMapper.toResponsedto(productFeedbackRepository.save(productFeedack));
        }
        return null;
    }

    @Override
    public List<ProductFeedbackResponse> getAllFeedback() {
        List<ProductFeedback> feedbacks = productFeedbackRepository.findAll();
        return feedbacks.stream()
                .map(productFeedbackMapper::toResponsedto)
                .collect(Collectors.toList());
    }


    @Override
    public List<ProductFeedbackResponse> getFeedbackByCustomerId(String customerId) {
        List<ProductFeedback> feedbacks = productFeedbackRepository.findByCustomerId(customerId);
        return feedbacks.stream().map(productFeedbackMapper::toResponsedto).collect(Collectors.toList());
    }

    @Override
    public List<ProductFeedbackResponse> getFeedbackByProductVariantId(String productVariantId) {
        List<ProductFeedback> feedbacks = productFeedbackRepository.findByProductVariantId(productVariantId);
        return feedbacks.stream()
                .map(productFeedbackMapper::toResponsedto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductFeedbackResponseV2> getFeedbackByProductVariantIdV2(String productVariantId) {
        List<ProductFeedback> feedbacks = productFeedbackRepository.findByProductVariantId(productVariantId);
        return feedbacks.stream()
                .map(productFeedbackMapper::toProductFeedbackResponseV2)
                .collect(Collectors.toList());
    }
    @Override
    public ProductFeedbackResponseV2 createFeedbackV2(ProductFeedbackRequestV2 productFeedbackRequestV2) {
        ProductFeedback productFeedback = productFeedbackMapper.toEntityV2(productFeedbackRequestV2);

        Customer customer = customerRepository.findById(productFeedbackRequestV2.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOTFOUND));

        String emailLogin = SecurityUtil.getCurrentUserLogin().orElse(null);
        if (emailLogin == null || !customer.getAccount().getEmail().equals(emailLogin)) {
            throw new AppException(ErrorCode.CUSTOMER_NOTMATCH_LOGIN);
        }

        ProductVariant productVariant = productVariantRepository.findById(productFeedbackRequestV2.getProductVariantId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));
        log.info("Product variant: {}", productVariant);
        if (productFeedbackRequestV2.getImagePaths() != null && !productFeedbackRequestV2.getImagePaths().isEmpty()) {
            log.info("Image paths: {}", productFeedbackRequestV2.getImagePaths());
            List<ImgProductFeedback> imgProductFeedbacks = productFeedbackRequestV2.getImagePaths().stream()
                    .filter(Objects::nonNull)
                    .map(imgPath -> {
                        ImgProductFeedback imgProductFeedback = new ImgProductFeedback();
                        imgProductFeedback.setImagePath(imgPath);
                        imgProductFeedback.setProductFeedback(productFeedback);
                        return imgProductFeedback;
                    })
                    .toList();

            productFeedback.setImgProductFeedbacks(imgProductFeedbacks);
        }
//        kha
        productFeedback.setCustomer_id(customer.getId());
        productFeedback.setProductVariant(productVariant);
        log.info("Product feedback to create: {}", productFeedback);
        ProductFeedback productFeedbackCreate = productFeedbackRepository.save(productFeedback);
        log.info("Product feedback created: {}", productFeedbackCreate);
        return productFeedbackMapper.toProductFeedbackResponseV2(productFeedbackCreate);
    }



}
