package iuh.fit.se.inventoryservice.event;

import iuh.fit.se.inventoryservice.entities.InventoryLog;
import iuh.fit.se.inventoryservice.entities.enumeration.ChangeType;
import iuh.fit.se.inventoryservice.repository.InventoryLogRepository;
import iuh.fit.se.inventoryservice.repository.InventoryRepository;
import iuh.fit.se.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryEventListener {

    private final InventoryService inventoryService;
    private final InventoryRepository inventoryRepository;
    private final InventoryLogRepository inventoryLogRepository;

    /**
     * ✅ Lắng nghe order.created để giữ hàng (nhiều sản phẩm cùng lúc)
     */
    @RabbitListener(queues = "order.created.queue")
    public void handleOrderCreated(OrderEvent orderEvent) {
        log.info("Received order.created event: {}", orderEvent);

        // Sử dụng service đã xử lý đầy đủ bên trong (reserve và gửi kết quả về)
        inventoryService.handleOrderCreatedEvent(orderEvent);
    }

    @RabbitListener(queues = "inventory.rollback.queue")
    public void handleInventoryRollback(InventoryUpdateMessage message) {
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
    }

    @RabbitListener(queues = "inventory.update.queue")
    public void handleInventoryUpdate(InventoryUpdateMessage message) {
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
                                .changeType(ChangeType.SOLD)
                                .orderId(message.getOrderId())
                                .inventory(inventory)
                                .changeReason("Thanh toán thành công")
                                .build());
                    });
        }
    }

}
