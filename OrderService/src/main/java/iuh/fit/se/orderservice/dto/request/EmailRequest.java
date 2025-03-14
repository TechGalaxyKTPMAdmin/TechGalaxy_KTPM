package iuh.fit.se.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    String orderCode;
    String paymentInfo;
    String shippingAddress;
    String orderNumber;
    String symbol;
    String invoiceDate;
    String invoiceNumber;
    String customerName;
    String taxCode;
    String searchCode;
}
