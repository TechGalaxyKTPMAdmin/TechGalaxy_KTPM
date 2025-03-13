package iuh.fit.se.inventoryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRollback {
    private String orderId;
    private String productVariantDetailId;
    private int quantity;
}
