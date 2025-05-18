package iuh.fit.se.productservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {

    @NotBlank(message = "Product variant detail ID cannot be blank")
    private String productVariantDetailId;

    @NotNull(message = "Stock quantity cannot be null")
    @Min(1)
    @Max(2000000000)
    private Integer stockQuantity;

    private Boolean internal = false;
}
