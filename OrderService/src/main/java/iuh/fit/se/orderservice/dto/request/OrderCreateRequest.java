package iuh.fit.se.orderservice.dto.request;

import iuh.fit.se.orderservice.entity.enumeration.OrderStatus;
import iuh.fit.se.orderservice.entity.enumeration.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreateRequest {
    String customerId;
    String systemUserId;
    String address;
    PaymentStatus paymentStatus = PaymentStatus.PENDING;
    OrderStatus orderStatus = OrderStatus.NEW;
    List<ProductDetailRequest> productDetailOrders;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
     public static class ProductDetailRequest {
        String productVariantDetailId;
        int quantity;
        Double price;
    }
}
