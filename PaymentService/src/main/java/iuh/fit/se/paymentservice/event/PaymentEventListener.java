package iuh.fit.se.paymentservice.event;

import com.rabbitmq.client.Channel;
import iuh.fit.se.paymentservice.dto.response.PaymentResponse;
import iuh.fit.se.paymentservice.dto.response.PaymentStatus;
import iuh.fit.se.paymentservice.dto.response.PaymentStatusResponse;
import iuh.fit.se.paymentservice.service.impl.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final PaymentServiceImpl paymentService;

    @Value("${rabbitmq.exchange.order}")
    private String orderExchange;

    @Value("${rabbitmq.routing-key.payment-completed}")
    private String paymentCompletedRoutingKey;

    @Value("${rabbitmq.routing-key.inventory-reserved-dlq}")
    private String inventoryReservedDlqRoutingKey;

    /**
     * ✅ Xử lý khi nhận được inventory.reserved.
     * Nếu COD -> trả PENDING.
     * Nếu VNPAY -> tạo link VNPAY và gửi lại cho OrderService.
     */
    @RabbitListener(queues = "${rabbitmq.queue.inventory-reserved}")
    public void handleInventoryReserved(OrderEvent event, Message message, Channel channel) throws IOException {
        log.info("📥 Received inventory.reserved for order: {}", event.getOrderId());

        try {
            // Nếu COD, gửi trạng thái PENDING về order.reply.queue
            if ("COD".equalsIgnoreCase(event.getPaymentMethod())) {
                rabbitTemplate.convertAndSend("order.reply.queue",
                        new PaymentStatusResponse(event.getOrderId(), PaymentStatus.PENDING, null));
                log.info("✅ Order {} is COD. Marked as PENDING", event.getOrderId());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }

            // Nếu VNPay -> tạo URL thanh toán
            PaymentResponse.VNPayResponseCreate vnPayUrl = paymentService.createVnPayPayment(event);
            log.info("✅ Created VNPay URL for order {}: {}", event.getOrderId(), vnPayUrl.getPaymentUrl());

            // Trả về OrderService qua order.reply.queue
            rabbitTemplate.convertAndSend("order.reply.queue",
                    new PaymentStatusResponse(event.getOrderId(), PaymentStatus.WAITING, vnPayUrl.getPaymentUrl()));

            // ACK khi thành công
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (Exception e) {
            log.error("❌ Failed to process payment for order {}: {}", event.getOrderId(), e.getMessage(), e);

            // Đẩy DLQ (không retry tiếp)
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);

            // Optional: Có thể gửi cảnh báo cho admin qua mail, slack
            rabbitTemplate.convertAndSend(orderExchange, inventoryReservedDlqRoutingKey, event);
        }
    }
}
