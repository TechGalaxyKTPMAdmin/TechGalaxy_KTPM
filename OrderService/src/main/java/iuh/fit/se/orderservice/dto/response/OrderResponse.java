package iuh.fit.se.orderservice.dto.response;

import iuh.fit.se.orderservice.entity.enumeration.OrderStatus;
import iuh.fit.se.orderservice.entity.enumeration.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String id;
    CustomerResponse customer;
    SystemUserResponse systemUser;
    String address;
    PaymentStatus paymentStatus;
    OrderStatus orderStatus;
    List<OrderDetailResponse> orderDetails;
    LocalDateTime createdAt;
}
