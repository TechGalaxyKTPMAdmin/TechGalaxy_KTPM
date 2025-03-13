package iuh.fit.se.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusResponse {
    private String orderId;
    private PaymentStatus status;
    private String paymentUrl;
}
