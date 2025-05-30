package iuh.fit.se.notificationservice.services.impl;

import iuh.fit.se.notificationservice.dto.request.EmailRequest;
import iuh.fit.se.notificationservice.exception.AppException;
import iuh.fit.se.notificationservice.exception.ErrorCode;

import iuh.fit.se.notificationservice.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.angus.mail.smtp.SMTPAddressFailedException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendSimpleEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setFrom(new InternetAddress("congtyviethan1234@gmail.com", "TechGalaxy"));
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content, isHtml);
            this.javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            log.error("Failed to send email", e);
            throw new AppException(ErrorCode.FAILED_SEND_EMAIL);
        } catch (Exception ex) {
            log.error("Invalid address: " + ex.getMessage());
        }
    }

    @Override
    public void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml)
            throws AppException {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setFrom(new InternetAddress("congtyviethan1234@gmail.com", "TechGalaxy"));
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content, isHtml);
            this.javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            log.error("Failed to send email", e);
            throw new AppException(ErrorCode.FAILED_SEND_EMAIL);
        } catch (Exception ex) {
            log.error("Invalid address: " + ex.getMessage());
        }
    }

    public void sendEmailFromTemplateSync(
            String to,
            String subject,
            String templateName,
            EmailRequest emailRequest) throws AppException {
        Context context = new Context();
        System.out.println("EmailRequest: " + emailRequest.toString());
        context.setVariable("orderCode", emailRequest.getOrderCode());
        context.setVariable("paymentInfo", emailRequest.getPaymentInfo());
        context.setVariable("shippingAddress", emailRequest.getShippingAddress());
        context.setVariable("orderNumber", emailRequest.getOrderNumber());
        context.setVariable("symbol", emailRequest.getSymbol());
        context.setVariable("invoiceDate", emailRequest.getInvoiceDate());
        context.setVariable("invoiceNumber", emailRequest.getInvoiceNumber());
        context.setVariable("customerName", emailRequest.getCustomerName());
        context.setVariable("taxCode", emailRequest.getTaxCode());
        context.setVariable("searchCode", emailRequest.getSearchCode());

        String content = templateEngine.process(templateName, context);
        this.sendEmailSync(to, subject, content, false, true);
    }

    public void sendEmailFromTemplateSync(String to, String subject, String templateName) throws AppException {
        Context context = new Context();
        String content = templateEngine.process(templateName, context);
        this.sendEmailSync(to, subject, content, false, true);
    }

    public void sendSuccessRegistrationEmail(String to, String subject, Map<String, Object> variables)
            throws MessagingException {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();

        Context context = new Context();
        context.setVariables(variables);

        String htmlContent = templateEngine.process("success-registration", context);

        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
        this.sendEmailSync(to, subject, htmlContent, true, true);

    }
}
