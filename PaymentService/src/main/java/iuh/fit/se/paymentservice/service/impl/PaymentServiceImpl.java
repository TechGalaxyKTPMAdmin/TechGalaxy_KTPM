package iuh.fit.se.paymentservice.service.impl;

import iuh.fit.se.paymentservice.config.VNPAYConfig;
import iuh.fit.se.paymentservice.dto.response.PaymentResponse;
import iuh.fit.se.paymentservice.event.OrderEvent;
import iuh.fit.se.paymentservice.service.PaymentService;
import iuh.fit.se.paymentservice.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Value("${payment.vnPay.url}")
    private String vnpayUrl;

    @Value("${payment.vnPay.tmnCode}")
    private String tmnCode;

    @Value("${payment.vnPay.secretKey}")
    private String secretKey;

    @Value("${payment.vnPay.returnUrl}")
    private String returnUrl;

    @Value("${payment.vnPay.version}")
    private String version;

    private final VNPAYConfig vnPayConfig;

    /**
     * ✅ Tạo link thanh toán VNPay theo OrderEvent truyền qua RabbitMQ
     *
     * @param event OrderEvent từ RabbitMQ
     * @return Link thanh toán VNPay
     */
    @Override
    public PaymentResponse.VNPayResponseCreate createVnPayPayment(OrderEvent event) {
        long amount = event.getTotalAmount() * 100L; // VNPay cần nhân 100

        // Lấy thông tin config mặc định từ VNPAYConfig
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();

        // Thêm các tham số bắt buộc
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toán đơn hàng: " + event.getOrderId());
        vnpParamsMap.put("vnp_TxnRef", event.getOrderId()); // Mã giao dịch là orderId
        vnpParamsMap.put("vnp_IpAddr", event.getIpAddress()); // Lấy IP từ OrderEvent

        // Tạo URL query và hash
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);

        // Hoàn thiện URL thanh toán
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        // Log để kiểm tra
        log.info("Generated VNPay payment URL for order {}: {}", event.getOrderId(), paymentUrl);

        // Trả về response
        return PaymentResponse.VNPayResponseCreate.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }

    public PaymentResponse.VNPayResponseCreate createVnPayPaymentURL(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_TxnR ef", VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" + VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentResponse.VNPayResponseCreate.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }
}
