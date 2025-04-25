package iuh.fit.se.orderservice.dto.request;

import iuh.fit.se.orderservice.entity.enumeration.OrderStatus;
import iuh.fit.se.orderservice.entity.enumeration.PaymentMethod;
import iuh.fit.se.orderservice.entity.enumeration.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequestV2 {
    @NotBlank(message = "Customer ID cannot be blank")
    String customerId;
    String systemUserId;
    @NotBlank(message = "Address cannot be blank")
    String address;
    @NotNull(message = "Payment method cannot be null")
    PaymentMethod paymentMethod;
    PaymentStatus paymentStatus = PaymentStatus.PENDING;
    OrderStatus orderStatus = OrderStatus.NEW;
    @NotNull(message = "Product detail orders must not be null")
    @Size(min = 1, message = "At least one product is required")
    List<ProductDetailOrder> productDetailOrders;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @ToString
    public static class ProductDetailOrder {
        @NotBlank(message = "Product variant detail ID cannot be blank")
        String productVariantDetailId;
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity;
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0")
        Double price;
    }
}
