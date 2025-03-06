package iuh.fit.se.orderservice.dto.request;

import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.dto.response.ProductVariantDetailResponse;
import iuh.fit.se.orderservice.entities.enumeration.DetailStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {
    String id;
    DetailStatus detailStatus;
    OrderResponse order;
    ProductVariantDetailResponse productVariantDetail;
    int quantity;
    double price;
}
