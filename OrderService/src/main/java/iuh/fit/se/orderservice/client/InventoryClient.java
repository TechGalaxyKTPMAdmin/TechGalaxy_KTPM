package iuh.fit.se.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "InventoryService")
public interface InventoryClient {
    @GetMapping("/inventory/check")
    Boolean checkStock(@RequestParam("productVariantDetailId") String productVariantDetailId,
            @RequestParam("quantity") int quantity);
}
