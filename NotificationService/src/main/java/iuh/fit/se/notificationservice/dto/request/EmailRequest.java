package iuh.fit.se.notificationservice.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
