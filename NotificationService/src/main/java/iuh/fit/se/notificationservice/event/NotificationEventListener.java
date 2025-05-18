package iuh.fit.se.notificationservice.event;

import com.rabbitmq.client.Channel;
import iuh.fit.se.notificationservice.dto.request.EmailRequest;
import iuh.fit.se.notificationservice.dto.request.NotificationDto;
import iuh.fit.se.notificationservice.entities.EmailLog;
import iuh.fit.se.notificationservice.entities.enumeration.EmailLogStatus;
import iuh.fit.se.notificationservice.exception.AppException;
import iuh.fit.se.notificationservice.repository.EmailLogRepository;
import iuh.fit.se.notificationservice.services.EmailService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {

    private final EmailService emailService;
    private final EmailLogRepository emailLogRepository;

    @RabbitListener(queues = "notification.queue")
    public void handleNotification(NotificationDto notificationDto, Message message, Channel channel)
            throws IOException {
        log.info("📩 [Notification Service] Received notification event: {}", notificationDto);

        String to = notificationDto.getEmail();
        String subject = notificationDto.getSubject();
        String type = notificationDto.getType();
        EmailRequest emailRequest = notificationDto.getEmailRequest();

        if (emailLogRepository.existsByOrderIdAndStatus(notificationDto.getOrderId(), EmailLogStatus.valueOf(type))) {
            log.warn("⚠️ Email for order {} with type {} already sent. Skipping!", notificationDto.getOrderId(), type);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        try {
            switch (type.toUpperCase()) {
                case "PAYMENT_PAID" -> {
                    System.out.println("Gửi email tới " + to);
                    emailRequest.setPaymentInfo("VNPAY");
                    emailService.sendEmailFromTemplateSync(
                            to, subject, "email-template-payment-success", emailRequest);
                    log.info("✅ Sent PAYMENT_PAID notification to {}", to);
                }
                case "PAYMENT_FAILED" -> {
                    emailService.sendSimpleEmail(
                            to, subject, notificationDto.getMessage(), false, false);
                    log.info("✅ Sent PAYMENT_FAILED notification to {}", to);
                }
                default -> {
                    log.warn("❗ Unknown notification type: {}. Rejecting message.", type);
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // Đẩy qua DLQ
                    return;
                }
            }
            System.out.println("Gửi email thành công tới " + to);
            // ✅ Nếu gửi email thành công, lưu log để không gửi trùng
            emailLogRepository.save(EmailLog.builder()
                    .orderId(notificationDto.getOrderId())
                    .email(to)
                    .status(EmailLogStatus.valueOf(type))
                    .sentAt(LocalDateTime.now())
                    .build());

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (MessagingException e) {
            log.error("📡 [SMTP ERROR] Gửi email thất bại tới {}. Sẽ retry! Error: {}", to, e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // Retry

        } catch (AppException e) {
            log.error("❌ [App ERROR] Gửi email thất bại tới {}. Đẩy qua DLQ. Error: {}", to, e.getMessage(), e);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("❌ [Unhandled ERROR] Không xử lý được email tới {}. Đẩy qua DLQ. Error: {}", to, e.getMessage(),
                    e);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
