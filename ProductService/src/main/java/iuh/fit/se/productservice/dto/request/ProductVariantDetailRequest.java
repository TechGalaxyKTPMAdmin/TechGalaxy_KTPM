package iuh.fit.se.productservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantDetailRequest {

    @NotBlank(message = "MEMORY_ID_NOT_EMPTY")
    String memid;

    @NotNull(message = "PRICE_NOT_NULL")
    @Min(value = 0, message = "PRICE_MUST_BE_POSITIVE")
    Double price;

    @NotNull(message = "SALE_NOT_NULL")
    @Min(value = 0, message = "SALE_MUST_BE_POSITIVE")
    Double sale;

    @NotNull(message = "COLORS_LIST_NOT_NULL")
    @Valid
    List<ColorRequest> colors;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ColorRequest {

        @Min(value = 0, message = "QUANTITY_MUST_BE_POSITIVE")
        int quantity;

        @NotBlank(message = "COLOR_ID_NOT_EMPTY")
        String colorId;
    }
}
