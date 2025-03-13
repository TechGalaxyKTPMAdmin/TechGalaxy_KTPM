package iuh.fit.se.userservice.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String id;
    private String customerId;
    private String systemUserId;
    private String paymentStatus;
    private String address;
    private String orderStatus;
    private String paymentMethod;
    private String vnp_TxnRef;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}