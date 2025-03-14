package iuh.fit.se.orderservice.event;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryUpdateMessage {
    private String orderId;
    private List<ProductVariantDetail> productVariantDetails;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductVariantDetail {
        private String productVariantDetailId;
        private int quantity;
    }
}
