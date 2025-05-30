package iuh.fit.se.orderservice.dto.response;

import java.io.Serializable;

import iuh.fit.se.orderservice.entity.enumeration.DetailStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse implements Serializable{
    private static final long serialVersionUID = 1L;
    String id;
    DetailStatus detailStatus;
    OrderResponse order;
    ProductVariantDetailResponse productVariantDetail;
    int quantity;
    double price;
}
