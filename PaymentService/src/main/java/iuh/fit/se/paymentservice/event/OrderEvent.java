package iuh.fit.se.paymentservice.event;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderEvent {
    private String orderId;
    private String customerId;
    private String paymentMethod;
    private Long totalAmount;
    private String ipAddress;
    private List<ProductVariantDetail> productVariantDetails;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class ProductVariantDetail {
        private String productVariantDetailId;
        private int quantity;

    }
}
