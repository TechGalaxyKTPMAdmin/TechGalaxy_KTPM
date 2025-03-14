package iuh.fit.se.notificationservice.dto.request;

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
    private String email;
    private EmailRequest emailRequest;
}
