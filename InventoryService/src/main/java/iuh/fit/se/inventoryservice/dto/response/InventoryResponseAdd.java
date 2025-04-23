package iuh.fit.se.inventoryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseAdd {
    private String productVariantDetailId;
    private int stock;
    private boolean success;
}
