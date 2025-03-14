package iuh.fit.se.orderservice.dto.request;

import iuh.fit.se.orderservice.dto.response.CustomerResponseV2;
import iuh.fit.se.orderservice.dto.response.SystemUserResponse;
import iuh.fit.se.orderservice.entity.enumeration.OrderStatus;
import iuh.fit.se.orderservice.entity.enumeration.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    String id;
    CustomerResponseV2 customer;
    SystemUserResponse systemUser;
    String address;
    PaymentStatus paymentStatus;
    OrderStatus orderStatus;
}
