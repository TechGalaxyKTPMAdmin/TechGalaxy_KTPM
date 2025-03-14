package iuh.fit.se.notificationservice.event;

import iuh.fit.se.notificationservice.dto.request.EmailRequest;
import iuh.fit.se.notificationservice.dto.request.NotificationDto;
import iuh.fit.se.notificationservice.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {

    private final EmailService emailService;

    @RabbitListener(queues = "notification.queue")
    public void handleNotification(NotificationDto notificationDto) {
        log.info("Received notification event: {}", notificationDto);

        String to = notificationDto.getEmail();
        String subject = notificationDto.getSubject();
        String type = notificationDto.getType();
        EmailRequest emailRequest = notificationDto.getEmailRequest();

        try {
            switch (type.toUpperCase()) {
                case "ORDER_CREATED" -> {
                    emailService.sendEmailFromTemplateSync(
                            to,
                            subject,
                            "email-template-order",
                            emailRequest);
                    log.info("Sent ORDER_CREATED notification to {}", to);
                }
                case "PAYMENT_PAID" -> {

                    emailService.sendEmailFromTemplateSync(
                            to,
                            subject,
                            "email-template-payment-success",
                            emailRequest);
                    log.info("Sent PAYMENT_SUCCESS notification to {}", to);
                }
                case "PAYMENT_FAILED" -> {
                    emailService.sendSimpleEmail(
                            to,
                            subject,
                            notificationDto.getMessage(),
                            false,
                            false);
                    log.info("Sent PAYMENT_FAILED notification to {}", to);
                }
                default -> log.warn("Unknown notification type: {}", type);
            }
        } catch (Exception e) {
            log.error("Failed to send notification to {}: {}", to, e.getMessage());
        }
    }
}
