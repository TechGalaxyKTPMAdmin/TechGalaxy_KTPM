package iuh.fit.se.inventoryservice.event;

import iuh.fit.se.inventoryservice.entities.InventoryLog;
import iuh.fit.se.inventoryservice.entities.enumeration.ChangeType;
import iuh.fit.se.inventoryservice.repository.InventoryLogRepository;
import iuh.fit.se.inventoryservice.repository.InventoryRepository;
import iuh.fit.se.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
// @RequiredArgsConstructor
@Slf4j
public class InventoryEventListener {

    private final InventoryService inventoryService;
    private final InventoryRepository inventoryRepository;
    private final InventoryLogRepository inventoryLogRepository;
    private final RabbitTemplate rabbitTemplate;

    public InventoryEventListener(InventoryService inventoryService, InventoryRepository inventoryRepository,
            InventoryLogRepository inventoryLogRepository, RabbitTemplate rabbitTemplate) {
        this.inventoryService = inventoryService;
        this.inventoryRepository = inventoryRepository;
        this.inventoryLogRepository = inventoryLogRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.exchange.order}")
    private String orderExchange;

    @Value("${rabbitmq.routing-key.order-created-dlq}")
    private String orderCreatedDlqRoutingKey;

    @Value("${rabbitmq.routing-key.inventory-update-dlq}")
    private String inventoryUpdateDlqRoutingKey;

    // ✅ Lắng nghe order.created để giữ hàng (nhiều sản phẩm cùng lúc) + try-catch
    @RabbitListener(queues = "order.created.queue")
    public void handleOrderCreated(OrderEvent orderEvent) {
        log.info("Received order.created event: {}", orderEvent);
        try {
            inventoryService.handleOrderCreatedEvent(orderEvent);
        } catch (Exception e) {
            log.error("❌ Error handling order.created: {}", e.getMessage(), e);
            rabbitTemplate.convertAndSend(orderExchange, orderCreatedDlqRoutingKey, orderEvent);
        }
    }

    @RabbitListener(queues = "inventory.rollback.queue")
    public void handleInventoryRollback(InventoryUpdateMessage message) {
        log.info("Received inventory.rollback: {}", message);
        try {
            for (InventoryUpdateMessage.ProductVariantDetail detail : message.getProductVariantDetails()) {
                inventoryRepository.findByProductVariantDetailId(detail.getProductVariantDetailId())
                        .ifPresent(inventory -> {
                            inventory.setStockQuantity(inventory.getStockQuantity() + detail.getQuantity());
                            inventory.setReservedQuantity(
                                    Math.max(0, inventory.getReservedQuantity() - detail.getQuantity()));
                            inventoryRepository.save(inventory);

                            // Ghi log rollback
                            inventoryLogRepository.save(InventoryLog.builder()
                                    .productVariantDetailId(detail.getProductVariantDetailId())
                                    .changeQuantity(detail.getQuantity())
                                    .changeType(ChangeType.ROLLBACK)
                                    .orderId(message.getOrderId())
                                    .inventory(inventory)
                                    .changeReason("Hoàn kho do thanh toán thất bại")
                                    .build());
                        });
            }
        } catch (Exception e) {
            log.error("❌ Error handling inventory.rollback: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "inventory.update.queue")
    public void handleInventoryUpdate(InventoryUpdateMessage message) {
        log.info("Received inventory.update: {}", message);
        try {
            for (InventoryUpdateMessage.ProductVariantDetail detail : message.getProductVariantDetails()) {
                inventoryRepository.findByProductVariantDetailId(detail.getProductVariantDetailId())
                        .ifPresent(inventory -> {
                            inventory.setReservedQuantity(
                                    Math.max(0, inventory.getReservedQuantity() - detail.getQuantity()));
                            inventoryRepository.save(inventory);

                            // Ghi log
                            inventoryLogRepository.save(InventoryLog.builder()
                                    .productVariantDetailId(detail.getProductVariantDetailId())
                                    .changeQuantity(-detail.getQuantity())
                                    .changeType(ChangeType.STOCK_OUT)
                                    .orderId(message.getOrderId())
                                    .inventory(inventory)
                                    .changeReason("Thanh toán thành công")
                                    .build());
                        });
            }
        } catch (Exception e) {
            log.error("❌ Error handling inventory.update: {}", e.getMessage(), e);

            rabbitTemplate.convertAndSend(orderExchange, inventoryUpdateDlqRoutingKey, message);
        }
    }
}
