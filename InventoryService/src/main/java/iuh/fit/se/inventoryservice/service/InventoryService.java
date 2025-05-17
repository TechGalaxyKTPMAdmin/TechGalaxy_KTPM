package iuh.fit.se.inventoryservice.service;

import iuh.fit.se.inventoryservice.dto.request.InventoryRequest;
import iuh.fit.se.inventoryservice.entities.Inventory;
import iuh.fit.se.inventoryservice.entities.InventoryLog;
import iuh.fit.se.inventoryservice.entities.enumeration.ChangeType;
import iuh.fit.se.inventoryservice.event.OrderEvent;
import iuh.fit.se.inventoryservice.repository.InventoryLogRepository;
import iuh.fit.se.inventoryservice.repository.InventoryRepository;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryLogRepository inventoryLogRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.order}")
    private String orderExchange;

    @Value("${rabbitmq.routing-key.inventory-reserved}")
    private String inventoryReservedRoutingKey;

    @Value("${rabbitmq.routing-key.inventory-failed}")
    private String inventoryFailedRoutingKey;

    /**
     * ✅ Kiểm tra tồn kho qua API Feign
     */
    public boolean checkStock(String productVariantDetailId, int quantity) {
        return inventoryRepository.findByProductVariantDetailId(productVariantDetailId)
                .map(inventory -> inventory.getStockQuantity() >= quantity)
                .orElse(false);
    }

    /**
     * ✅ Xử lý giữ kho khi nhận order.created (nhiều sản phẩm)
     */
    @Transactional
    public void handleOrderCreatedEvent(OrderEvent orderEvent) {
        log.info("Received order.created event: {}", orderEvent);

        boolean allReserved = true; // Đánh dấu kiểm tra xem có giữ được toàn bộ không

        for (OrderEvent.ProductVariantDetail detail : orderEvent.getProductVariantDetails()) {
            boolean reserved = reserveStock(orderEvent.getOrderId(), detail.getProductVariantDetailId(),
                    detail.getQuantity());
            if (!reserved) {
                allReserved = false;
                break;
            }
        }

        if (allReserved) {
            // ✅ Nếu tất cả giữ được hàng
            rabbitTemplate.convertAndSend(orderExchange, inventoryReservedRoutingKey, orderEvent);
            log.info("Inventory reserved successfully for orderId: {}", orderEvent.getOrderId());
        } else {
            // ❌ Nếu không giữ được toàn bộ => Gửi event thất bại
            rabbitTemplate.convertAndSend(orderExchange, inventoryFailedRoutingKey, orderEvent);
            log.warn("Inventory insufficient for orderId: {}", orderEvent.getOrderId());
        }
    }

    /**
     * ✅ Hàm giữ kho Idempotent (chống giữ lặp) cho từng đơn hàng và sản phẩm
     */
    @Transactional
    public boolean reserveStock(String orderId, String productVariantDetailId, int quantity) {
        // 1. Kiểm tra đã giữ kho cho đơn này chưa
        boolean isReserved = inventoryLogRepository.existsByOrderIdAndProductVariantDetailIdAndChangeType(
                orderId,
                productVariantDetailId,
                ChangeType.RESERVED);

        if (isReserved) {
            log.warn("Order {} already reserved product {}. Skip!", orderId, productVariantDetailId);
            return true;
        }

        // 2. Lấy thông tin tồn kho
        Optional<Inventory> optInventory = inventoryRepository.findByProductVariantDetailId(productVariantDetailId);

        if (optInventory.isPresent()) {
            Inventory inventory = optInventory.get();

            // 3. Kiểm tra đủ hàng để giữ
            if (inventory.getStockQuantity() >= quantity) {
                // 4. Cập nhật tồn kho
                inventory.setStockQuantity(inventory.getStockQuantity() - quantity);
                inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
                inventoryRepository.save(inventory);

                // 5. Ghi log giữ kho
                InventoryLog inventoryLog = InventoryLog.builder()
                        .productVariantDetailId(productVariantDetailId)
                        .changeQuantity(-quantity)
                        .changeType(ChangeType.RESERVED)
                        .orderId(orderId)
                        .inventory(inventory)
                        .changeReason("Reserved for order: " + orderId)
                        .build();
                inventoryLogRepository.save(inventoryLog);

                log.info("Reserved {} of product {} for order {}", quantity, productVariantDetailId, orderId);
                return true;
            } else {
                log.warn("Not enough stock for product {}. Requested: {}, Available: {}",
                        productVariantDetailId, quantity, inventory.getStockQuantity());
            }
        } else {
            log.error("Product {} not found in inventory", productVariantDetailId);
        }

        return false;
    }

    /**
     * ✅ Rollback khi đơn hàng bị hủy hoặc thanh toán thất bại (nhiều sản phẩm)
     */
    @Transactional
    public void rollbackStock(OrderEvent orderEvent) {
        log.info("Rolling back inventory for order: {}", orderEvent.getOrderId());

        for (OrderEvent.ProductVariantDetail detail : orderEvent.getProductVariantDetails()) {
            rollbackProduct(orderEvent.getOrderId(), detail.getProductVariantDetailId(), detail.getQuantity());
        }

        log.info("Inventory rolled back successfully for order: {}", orderEvent.getOrderId());
    }

    /**
     * ✅ Rollback cho từng sản phẩm
     */
    private void rollbackProduct(String orderId, String productVariantDetailId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductVariantDetailId(productVariantDetailId)
                .orElseThrow(() -> new RuntimeException("Product not found for rollback: " + productVariantDetailId));

        // 1. Cập nhật tồn kho
        inventory.setStockQuantity(inventory.getStockQuantity() + quantity);
        inventory.setReservedQuantity(inventory.getReservedQuantity() - quantity);
        inventoryRepository.save(inventory);

        // 2. Ghi log hoàn kho
        InventoryLog log = InventoryLog.builder()
                .productVariantDetailId(productVariantDetailId)
                .changeQuantity(quantity)
                .changeType(ChangeType.ROLLBACK)
                .orderId(orderId)
                .inventory(inventory)
                .changeReason("Rollback for order: " + orderId)
                .build();
        inventoryLogRepository.save(log);
    }

    public Inventory getInventoryByProductVariantDetailId(String productVariantDetailId) {
        return inventoryRepository.findByProductVariantDetailId(productVariantDetailId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productVariantDetailId));
    }

    public Inventory getInventoryById(String id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found: " + id));
    }

    public Inventory saveOrUpdateInventory(InventoryRequest inventoryRequest) {
        Inventory existingInventory = inventoryRepository
                .findByProductVariantDetailId(inventoryRequest.getProductVariantDetailId()).orElse(null);
        if (existingInventory != null) {
            existingInventory
                    .setStockQuantity(inventoryRequest.getStockQuantity());
            return inventoryRepository.save(existingInventory);
        }
        Inventory inventory = new Inventory();
        inventory.setProductVariantDetailId(inventoryRequest.getProductVariantDetailId());
        inventory.setStockQuantity(inventoryRequest.getStockQuantity());
        inventory.setReservedQuantity(0);
        return inventoryRepository.save(inventory);
    }

}
