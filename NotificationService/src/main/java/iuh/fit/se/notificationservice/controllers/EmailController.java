package iuh.fit.se.notificationservice.controllers;

import iuh.fit.se.notificationservice.dto.request.EmailRequest;
import iuh.fit.se.notificationservice.dto.request.EmailRequestRegister;
import iuh.fit.se.notificationservice.services.impl.EmailServiceImpl;

import iuh.fit.se.notificationservice.util.SecurityUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class EmailController {
    private final EmailServiceImpl emailService;

    @Autowired
    public EmailController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email")
    public String sendEmail(@RequestBody EmailRequest request) {
        String emailLogin = SecurityUtil.getCurrentUserLogin().orElse(null);
        System.out.println("emailLogin: " + emailLogin);
        EmailRequest emailRequest = new EmailRequest();
         emailRequest.setOrderCode(request.getOrderCode());
         emailRequest.setPaymentInfo(request.getPaymentInfo());
         emailRequest.setShippingAddress(request.getShippingAddress());
         emailRequest.setOrderNumber(request.getOrderNumber());
         emailRequest.setSymbol(request.getSymbol());
         emailRequest.setInvoiceDate(LocalDate.now().toString());
         emailRequest.setInvoiceNumber(request.getInvoiceNumber());
         emailRequest.setCustomerName(request.getCustomerName());
         emailRequest.setTaxCode(request.getTaxCode());
         emailRequest.setSearchCode(request.getSearchCode());


        emailService.sendEmailFromTemplateSync(
                emailLogin,
                "Hóa đơn mua hàng",
                "email-template",
                emailRequest);

        return "ok";
    }

    @PostMapping("/email/test")
    public String sendEmail() {
        String emailLogin = SecurityUtil.getCurrentUserLogin().orElse(null);
        System.out.println("emailLogin: " + emailLogin);
        EmailRequest emailRequest = new EmailRequest();

        emailRequest.setOrderCode("request.getOrderCode()");
        emailRequest.setPaymentInfo("request.getPaymentInfo()");
        emailRequest.setShippingAddress("request.getShippingAddress()");
        emailRequest.setOrderNumber("request.getOrderNumber()");
        emailRequest.setSymbol("request.getSymbol()");
        emailRequest.setInvoiceDate(LocalDate.now().toString());
        emailRequest.setInvoiceNumber("request.getInvoiceNumber()");
        emailRequest.setCustomerName("request.getCustomerName()");
        emailRequest.setTaxCode("request.getTaxCode()");
        emailRequest.setSearchCode("request.getSearchCode()");
        emailService.sendEmailFromTemplateSync(
                emailLogin,
                "Hóa đơn mua hàng",
                "email-template",
                emailRequest);

        return "ok";
    }

    @PostMapping("/email/success-registration")
    public String sendSuccessRegistrationEmail(@RequestBody EmailRequestRegister request) throws MessagingException {
        String emailLogin = SecurityUtil.getCurrentUserLogin().orElse(null);

        Map<String, Object> variables = Map.of(
                "name", request.getName(),
                "email", request.getEmail(),
                "registrationDate",  LocalDateTime.now().toString()
        );

        emailService.sendSuccessRegistrationEmail(
                emailLogin,
                "TechGalaxy Registration",
                variables);

        return "ok";
    }
}
