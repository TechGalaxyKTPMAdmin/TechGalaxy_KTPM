package iuh.fit.se.notificationservice.services;

import iuh.fit.se.notificationservice.dto.request.EmailRequest;

public interface EmailService {
    void sendSimpleEmail();

    void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml);

    void sendEmailFromTemplateSync(
            String to,
            String subject,
            String templateName,
            EmailRequest emailRequest
    );

    void sendEmailFromTemplateSync(String to, String subject, String templateName);
}
