package iuh.fit.se.orderservice.dto.request;

import iuh.fit.se.orderservice.entity.enumeration.OrderStatus;
import iuh.fit.se.orderservice.entity.enumeration.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateRequest {
    String id;
    String address;
    PaymentMethod paymentMethod;
    PaymentStatus paymentStatus;
    OrderStatus orderStatus;
}
