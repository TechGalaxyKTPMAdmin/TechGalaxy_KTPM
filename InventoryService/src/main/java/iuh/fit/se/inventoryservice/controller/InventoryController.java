package iuh.fit.se.inventoryservice.controller;

import iuh.fit.se.inventoryservice.dto.request.InventoryRequest;
import iuh.fit.se.inventoryservice.dto.response.DataResponse;
import iuh.fit.se.inventoryservice.dto.response.InventoryResponse;
import iuh.fit.se.inventoryservice.dto.response.InventoryResponseAdd;
import iuh.fit.se.inventoryservice.entities.Inventory;
import iuh.fit.se.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    // API thêm tồn kho
    @PostMapping("/save")
    public ResponseEntity<DataResponse<InventoryResponseAdd>> saveInventory(
            @Valid @RequestBody InventoryRequest inventory) {
        System.out.println("InventoryRequest: " + inventory);
        Inventory inventoryAdd = inventoryService.saveOrUpdateInventory(inventory);
        if (inventoryAdd == null) {
            return ResponseEntity.badRequest().build();
        }
        InventoryResponseAdd inventoryResponse = new InventoryResponseAdd(inventoryAdd.getProductVariantDetailId(),
                inventoryAdd.getStockQuantity(), true);
        List<InventoryResponseAdd> inventoryResponses = new ArrayList<>();
        inventoryResponses.add(inventoryResponse);
        return ResponseEntity.ok(DataResponse.<InventoryResponseAdd>builder()
                .message("Create order success")
                .data(inventoryResponses)
                .build());
    }

}
