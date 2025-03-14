package iuh.fit.se.orderservice.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    private String orderId;
    private String customerId;
    private String type;
    private String subject;
    private String message;
    private EmailRequest emailRequest;
}
