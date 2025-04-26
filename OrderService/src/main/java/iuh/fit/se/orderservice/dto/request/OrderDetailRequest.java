package iuh.fit.se.orderservice.dto.request;

import iuh.fit.se.orderservice.dto.response.OrderResponse;
import iuh.fit.se.orderservice.dto.response.ProductVariantDetailResponse;
import iuh.fit.se.orderservice.entity.enumeration.DetailStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {

    @NotBlank(message = "ORDER_DETAIL_ID_NOT_EMPTY")
    String id;

    @NotNull(message = "DETAIL_STATUS_NOT_NULL")
    DetailStatus detailStatus;

    @NotNull(message = "ORDER_INFO_REQUIRED")
    OrderResponse order;

    @NotNull(message = "PRODUCT_VARIANT_DETAIL_INFO_REQUIRED")
    ProductVariantDetailResponse productVariantDetail;

    @NotNull(message = "QUANTITY_REQUIRED")
    @Min(value = 1, message = "QUANTITY_MUST_BE_AT_LEAST_1")
    int quantity;

    @NotNull(message = "PRICE_REQUIRED")
    @DecimalMin(value = "0.0", message = "PRICE_MUST_BE_POSITIVE")
    double price;
}
