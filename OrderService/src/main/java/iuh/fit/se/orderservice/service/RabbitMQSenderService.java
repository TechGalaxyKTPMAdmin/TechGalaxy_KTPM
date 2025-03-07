package iuh.fit.se.orderservice.service;

import iuh.fit.se.orderservice.dto.response.OrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Service
public class RabbitMQSenderService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.order}")
    private String orderExchange;

    @Value("${rabbitmq.routing-key.order-created}")
    private String orderCreatedRoutingKey;

    @Value("${rabbitmq.routing-key.order-status-updated}")
    private String orderStatusUpdatedRoutingKey;

    @Value("${rabbitmq.routing-key.inventory-update}")
    private String inventoryUpdateRoutingKey;

    @Value("${rabbitmq.routing-key.notification}")
    private String notificationRoutingKey;

    @Autowired
    public RabbitMQSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderCreatedEvent(OrderResponse orderResponse) {
        rabbitTemplate.convertAndSend(orderExchange, orderCreatedRoutingKey, orderResponse);
    }

//    public void sendOrderStatusUpdatedEvent(Long orderId, String orderNumber, String status) {
//        OrderStatusUpdate statusUpdate = OrderStatusUpdate.builder()
//                .orderId(orderId)
//                .orderNumber(orderNumber)
//                .status(status)
//                .build();
//
//        rabbitTemplate.convertAndSend(orderExchange, orderStatusUpdatedRoutingKey, statusUpdate);
//    }
//
//    public void sendOrderNotification(OrderNotificationDTO notification) {
//        rabbitTemplate.convertAndSend(orderExchange, notificationRoutingKey, notification);
//    }
//
//    public void sendOrderStatusNotification(OrderStatusNotificationDTO notification) {
//        rabbitTemplate.convertAndSend(orderExchange, notificationRoutingKey, notification);
//    }
//
//    public void sendInventoryUpdate(InventoryUpdateDTO inventoryUpdate) {
//        rabbitTemplate.convertAndSend(orderExchange, inventoryUpdateRoutingKey, inventoryUpdate);
//    }
}
