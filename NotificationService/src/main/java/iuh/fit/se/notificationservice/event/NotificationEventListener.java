package iuh.fit.se.notificationservice.event;

import com.rabbitmq.client.Channel;
import iuh.fit.se.notificationservice.dto.request.EmailRequest;
import iuh.fit.se.notificationservice.dto.request.NotificationDto;
import iuh.fit.se.notificationservice.services.EmailService;

import jakarta.mail.SendFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {

    private final EmailService emailService;

    @RabbitListener(queues = "notification.queue")
    public void handleNotification(NotificationDto notificationDto, Message message, Channel channel)
            throws IOException {
        log.info("📩 Received notification event: {}", notificationDto);

        String to = notificationDto.getEmail();
        String subject = notificationDto.getSubject();
        String type = notificationDto.getType();
        EmailRequest emailRequest = notificationDto.getEmailRequest();

        try {
            switch (type.toUpperCase()) {
                case "ORDER_CREATED" -> {
                    emailService.sendEmailFromTemplateSync(to, subject, "email-template-order", emailRequest);
                    log.info("✅ Sent ORDER_CREATED notification to {}", to);
                }
                case "PAYMENT_PAID" -> {
                    emailService.sendEmailFromTemplateSync(to, subject, "email-template-payment-success", emailRequest);
                    log.info("✅ Sent PAYMENT_PAID notification to {}", to);
                }
                case "PAYMENT_FAILED" -> {
                    emailService.sendSimpleEmail(to, subject, notificationDto.getMessage(), false, false);
                    log.info("✅ Sent PAYMENT_FAILED notification to {}", to);
                }
                default -> {
                    log.warn("❗ Unknown notification type: {}", type);
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                    return;
                }
            }

            // ✅ Nếu gửi thành công, xác nhận RabbitMQ message
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (MessagingException e) {
            log.error("📡 SMTP lỗi - Thử lại email: {} | Error: {}", to, e.getMessage());

            // Retry lại message nếu lỗi SMTP tạm thời
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        } catch (Exception e) {
            log.error("❌ Lỗi không xác định khi gửi email: {} | Error: {}", to, e.getMessage(), e);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
