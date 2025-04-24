package iuh.fit.se.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import iuh.fit.se.productservice.dto.request.InventoryRequest;
import iuh.fit.se.productservice.dto.response.DataResponse;
import iuh.fit.se.productservice.dto.response.InventoryResponseAdd;

@FeignClient(name = "InventoryService")
public interface InventoryClient {
    @PostMapping("/save")
    DataResponse<InventoryResponseAdd> addInventory(@RequestBody InventoryRequest inventory);
}
