package com.eve_coding.eveshop.utils;

import com.eve_coding.eveshop.model.Order;
import com.eve_coding.eveshop.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendEmail {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public void sendConfirmationEmail(User user, String token){
        String emailText =  "Hi " + user.getFirstName() + "!, please confirm your account by clicking on this link" + " http://localhost:8080/account/confirmation/" + token+"/"+user.getEmail();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Account confirmation");
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("muthanunie@gmail.com");
        mailMessage.setText(emailText);
        javaMailSender.send(mailMessage);
    }

    public void sendOrderConfirmationEmail(User user, Order order){
        Context context = new Context();
        context.setVariable("order",order);
        context.setVariable("user",user);

        String emailTemplate = templateEngine.process("orderConfirmationEmailTemplate",context);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(@NonNull MimeMessage mimeMessage){
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
                try {
                    mimeMessageHelper.setTo(user.getEmail());
                    mimeMessageHelper.setSubject("Order Confirmation - " + order.getOrderId());
                    mimeMessageHelper.setText(emailTemplate,true);
                    mimeMessageHelper.setFrom(new InternetAddress("muthanunie@gmail.com"));
                } catch (MessagingException e) {
                    log.error("error sending message", e.fillInStackTrace());
                }
            }
        };

        javaMailSender.send(messagePreparator);
    }
}
