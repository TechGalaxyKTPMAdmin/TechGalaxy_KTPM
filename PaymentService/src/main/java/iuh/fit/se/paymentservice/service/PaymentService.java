package iuh.fit.se.paymentservice.service;

import iuh.fit.se.paymentservice.dto.response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    public PaymentResponse.VNPayResponseCreate createVnPayPayment(HttpServletRequest request) ;
}
