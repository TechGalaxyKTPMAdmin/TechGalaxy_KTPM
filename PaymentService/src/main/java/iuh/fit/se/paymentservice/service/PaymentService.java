package iuh.fit.se.paymentservice.service;

import iuh.fit.se.paymentservice.dto.response.PaymentResponse;
import iuh.fit.se.paymentservice.event.OrderEvent;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    public PaymentResponse.VNPayResponseCreate createVnPayPayment(OrderEvent event) ;

    public PaymentResponse.VNPayResponseCreate createVnPayPaymentURL(HttpServletRequest request);
}
