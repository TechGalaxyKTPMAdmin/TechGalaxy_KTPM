package iuh.fit.se.paymentservice.controller;

import iuh.fit.se.paymentservice.dto.response.DataResponse;
import iuh.fit.se.paymentservice.dto.response.PaymentResponse;
import iuh.fit.se.paymentservice.dto.response.PaymentStatus;
import iuh.fit.se.paymentservice.dto.response.PaymentStatusResponse;
import iuh.fit.se.paymentservice.service.impl.PaymentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
        private final PaymentServiceImpl paymentService;
        private final RabbitTemplate rabbitTemplate;

        @Value("${rabbitmq.exchange.order}")
        private String orderExchange;

        @Value("${rabbitmq.routing-key.payment-completed}")
        private String paymentCompletedRoutingKey;

        @Value("${rabbitmq.routing-key.payment-failed}")
        private String paymentFailedRoutingKey;

        @Autowired
        public PaymentController(PaymentServiceImpl paymentService, RabbitTemplate rabbitTemplate) {
                this.paymentService = paymentService;
                this.rabbitTemplate = rabbitTemplate;
        }

        // @GetMapping("/vn-pay")
        // public ResponseEntity<DataResponse<PaymentResponse.VNPayResponseCreate>>
        // pay(HttpServletRequest request) {
        // return
        // ResponseEntity.ok(DataResponse.<PaymentResponse.VNPayResponseCreate>builder()
        // .data(List.of(paymentService.createVnPayPaymentURL(request)))
        // .build());
        // }

        // @GetMapping("/vn-pay-callback")
        // public ResponseEntity<DataResponse<PaymentResponse.VNPayResponse>>
        // payCallbackHandler(HttpServletRequest request) {
        // String amount = request.getParameter("vnp_Amount");
        // String bankCode = request.getParameter("vnp_BankCode");
        // String bankTranNo = request.getParameter("vnp_BankTranNo");
        // String cardType = request.getParameter("vnp_CardType");
        // String orderInfo = request.getParameter("vnp_OrderInfo");
        // String payDate = request.getParameter("vnp_PayDate");
        // String responseCode = request.getParameter("vnp_ResponseCode");
        // String transactionNo = request.getParameter("vnp_TransactionNo");
        // String transactionStatus = request.getParameter("vnp_TransactionStatus");
        // String txnRef = request.getParameter("vnp_TxnRef");
        // String secureHash = request.getParameter("vnp_SecureHash");
        //
        // // Tạo đối tượng VNPayResponse
        // PaymentResponse.VNPayResponse vnPayResponse =
        // PaymentResponse.VNPayResponse.builder()
        // .txnRef(txnRef)
        // .amount(amount)
        // .orderInfo(orderInfo)
        // .responseCode(responseCode)
        // .transactionNo(transactionNo)
        // .bankCode(bankCode)
        // .bankTranNo(bankTranNo)
        // .cardType(cardType)
        // .payDate(payDate)
        // .transactionStatus(transactionStatus)
        // .secureHash(secureHash)
        // .signValue("")
        // .build();
        //
        // String status = "00".equals(responseCode) ? "PAID" : "FAILED";
        // PaymentStatus paymentStatus = status.equals("PAID") ? PaymentStatus.PAID :
        // PaymentStatus.FAILED;
        // String paymentStatusStr = status.equals("PAID") ? paymentCompletedRoutingKey
        // : paymentFailedRoutingKey;
        // rabbitTemplate.convertAndSend(orderExchange,
        // paymentStatusStr,
        // new PaymentStatusResponse(
        // txnRef,
        // paymentStatus,
        // null));
        //
        // return ResponseEntity.ok(
        // DataResponse.<PaymentResponse.VNPayResponse>builder()
        // .data(List.of(vnPayResponse))
        // .build());
        // }

        @GetMapping("/vn-pay-callback")
        public RedirectView payCallbackHandler(HttpServletRequest request) {
                // Lấy dữ liệu từ callback
                String responseCode = request.getParameter("vnp_ResponseCode");
                String txnRef = request.getParameter("vnp_TxnRef");
                String amount = request.getParameter("vnp_Amount");
                String orderInfo = request.getParameter("vnp_OrderInfo");
                String transactionNo = request.getParameter("vnp_TransactionNo");
                String bankCode = request.getParameter("vnp_BankCode");
                String payDate = request.getParameter("vnp_PayDate");
                String transactionStatus = request.getParameter("vnp_TransactionStatus");
                String bankTranNo = request.getParameter("vnp_BankTranNo");
                String cardType = request.getParameter("vnp_CardType");
                String secureHash = request.getParameter("vnp_SecureHash");

                // Xử lý trạng thái
                String status = "00".equals(responseCode) ? "PAID" : "FAILED";
                PaymentStatus paymentStatus = status.equals("PAID") ? PaymentStatus.PAID : PaymentStatus.FAILED;
                String routingKey = status.equals("PAID") ? paymentCompletedRoutingKey : paymentFailedRoutingKey;

                // Gửi message đến RabbitMQ
                rabbitTemplate.convertAndSend(orderExchange, routingKey,
                                new PaymentStatusResponse(txnRef, paymentStatus, null));

                // Redirect URL
                String redirectUrl = "http://localhost:8080/payment?" +
                                "vnp_TxnRef=" + txnRef +
                                "&vnp_Amount=" + amount +
                                "&vnp_OrderInfo=" + orderInfo +
                                "&vnp_ResponseCode=" + responseCode +
                                "&vnp_TransactionNo=" + transactionNo +
                                "&vnp_BankCode=" + bankCode +
                                "&vnp_PayDate=" + payDate +
                                "&vnp_TransactionStatus=" + transactionStatus +
                                "&vnp_BankTranNo=" + bankTranNo +
                                "&vnp_CardType=" + cardType +
                                "&vnp_SecureHash=" + secureHash;

                return new RedirectView(redirectUrl);
        }

}
