package iuh.fit.se.inventoryservice.service;

import iuh.fit.se.inventoryservice.entities.Inventory;
import iuh.fit.se.inventoryservice.entities.InventoryLog;
import iuh.fit.se.inventoryservice.entities.enumeration.ChangeType;
import iuh.fit.se.inventoryservice.event.OrderEvent;
import iuh.fit.se.inventoryservice.repository.InventoryLogRepository;
import iuh.fit.se.inventoryservice.repository.InventoryRepository;
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
            boolean reserved = reserveStock(orderEvent.getOrderId(), detail.getProductVariantDetailId(), detail.getQuantity());
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
     * ✅ Hàm giữ kho (riêng cho từng sản phẩm)
     */
    @Transactional
    public boolean reserveStock(String orderId, String productVariantDetailId, int quantity) {
        Optional<Inventory> optInventory = inventoryRepository.findByProductVariantDetailId(productVariantDetailId);

        if (optInventory.isPresent()) {
            Inventory inventory = optInventory.get();

            if (inventory.getStockQuantity() >= quantity) {
                // 1. Cập nhật tồn kho
                inventory.setStockQuantity(inventory.getStockQuantity() - quantity);
                inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
                inventoryRepository.save(inventory);

                // 2. Ghi log giữ kho
                InventoryLog inventoryLog = InventoryLog.builder()
                        .productVariantDetailId(productVariantDetailId)
                        .changeQuantity(-quantity)
                        .changeType(ChangeType.RESERVED)
                        .orderId(orderId)
                        .inventory(inventory)
                        .changeReason("Reserved for order: " + orderId)
                        .build();
                inventoryLogRepository.save(inventoryLog);

                return true;
            }
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

}
