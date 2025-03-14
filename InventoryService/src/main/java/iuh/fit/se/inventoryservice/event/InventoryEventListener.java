package iuh.fit.se.inventoryservice.event;

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

    /**
     * ✅ Lắng nghe order.created để giữ hàng (nhiều sản phẩm cùng lúc)
     */
    @RabbitListener(queues = "order.created.queue")
    public void handleOrderCreated(OrderEvent orderEvent) {
        log.info("Received order.created event: {}", orderEvent);

        // Sử dụng service đã xử lý đầy đủ bên trong (reserve và gửi kết quả về)
        inventoryService.handleOrderCreatedEvent(orderEvent);
    }

    /**
     * ✅ Lắng nghe inventory.rollback để hoàn kho (nhiều sản phẩm)
     */
    @RabbitListener(queues = "inventory.rollback.queue")
    public void handleInventoryRollback(OrderEvent orderEvent) {
        log.info("Received inventory.rollback event for order: {}", orderEvent.getOrderId());

        // Sử dụng service rollback đã cập nhật
        inventoryService.rollbackStock(orderEvent);

        log.info("Inventory rolled back successfully for order: {}", orderEvent.getOrderId());
    }

}
