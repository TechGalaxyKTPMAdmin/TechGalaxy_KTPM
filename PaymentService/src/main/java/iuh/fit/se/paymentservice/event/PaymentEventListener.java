package iuh.fit.se.paymentservice.event;

import iuh.fit.se.paymentservice.dto.response.PaymentResponse;
import iuh.fit.se.paymentservice.dto.response.PaymentStatus;
import iuh.fit.se.paymentservice.dto.response.PaymentStatusResponse;
import iuh.fit.se.paymentservice.service.impl.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    /**
     * ✅ Lắng nghe khi Inventory giữ hàng thành công (toàn bộ sản phẩm).
     * Nếu là COD thì trả về PENDING, nếu VNPay thì trả link về queue để Order lấy hiển thị cho FE.
     */
    @RabbitListener(queues = "inventory.reserved.queue")
    public void handleInventoryReserved(OrderEvent event) {
        log.info("Received inventory.reserved for order: {}", event.getOrderId());

        // Trường hợp thanh toán COD
        if ("COD".equalsIgnoreCase(event.getPaymentMethod())) {
            // Trả về trạng thái PENDING
            rabbitTemplate.convertAndSend("order.reply.queue",
                    new PaymentStatusResponse(event.getOrderId(), PaymentStatus.PENDING, null));
            log.info("Order {} is COD, marked as PENDING", event.getOrderId());
            return;
        }

        // Trường hợp thanh toán VNPay
        try {
            // Tạo URL thanh toán VNPay
            PaymentResponse.VNPayResponseCreate vnPayUrl = paymentService.createVnPayPayment(event);
            log.info("Created VNPay URL for order {}: {}", event.getOrderId(), vnPayUrl.getPaymentUrl());

            // Gửi link thanh toán qua order.reply.queue để OrderService nhận và trả về FE
            rabbitTemplate.convertAndSend("order.reply.queue",
                    new PaymentStatusResponse(event.getOrderId(), PaymentStatus.WAITING, vnPayUrl.getPaymentUrl()));

        } catch (Exception e) {
            log.error("Failed to create VNPay payment for order {}: {}", event.getOrderId(), e.getMessage());
            // Có thể gửi thông tin thất bại về queue khác để xử lý huỷ đơn, rollback inventory (nếu muốn)
        }
    }
}
