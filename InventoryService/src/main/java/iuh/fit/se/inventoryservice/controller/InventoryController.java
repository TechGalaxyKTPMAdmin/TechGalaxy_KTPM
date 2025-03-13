package iuh.fit.se.inventoryservice.controller;

import iuh.fit.se.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // API kiểm tra tồn kho
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkStock(
            @RequestParam String productVariantDetailId,
            @RequestParam int quantity) {
        boolean available = inventoryService.checkStock(productVariantDetailId, quantity);
        return ResponseEntity.ok(available);
    }
}
