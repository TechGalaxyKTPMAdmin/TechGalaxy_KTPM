package iuh.fit.se.orderservice.dto.request;

import iuh.fit.se.orderservice.dto.response.CustomerResponse;
import iuh.fit.se.orderservice.dto.response.SystemUserResponse;
import iuh.fit.se.orderservice.entity.enumeration.OrderStatus;
import iuh.fit.se.orderservice.entity.enumeration.PaymentMethod;
import iuh.fit.se.orderservice.entity.enumeration.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateRequest {

    @NotBlank(message = "ORDER_ID_NOT_EMPTY")
    String id;

    @NotNull(message = "CUSTOMER_INFO_REQUIRED")
    CustomerResponse customer;

    @NotNull(message = "SYSTEM_USER_INFO_REQUIRED")
    SystemUserResponse systemUser;

    @NotBlank(message = "ADDRESS_NOT_EMPTY")
    @Size(max = 500, message = "ADDRESS_MAX_LENGTH_500")
    String address;

    @NotNull(message = "PAYMENT_METHOD_NOT_NULL")
    PaymentMethod paymentMethod;

    @NotNull(message = "PAYMENT_STATUS_NOT_NULL")
    PaymentStatus paymentStatus;

    @NotNull(message = "ORDER_STATUS_NOT_NULL")
    OrderStatus orderStatus;
}
