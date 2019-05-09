package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.EmailContentDto;
import com.kabat.petfinder.entities.PetToken;
import com.kabat.petfinder.exceptions.EmailCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    private static Logger LOG = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;
    private final ITemplateEngine templateEngine;


    public EmailServiceImpl(JavaMailSender javaMailSender, ITemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    @Override
    public void sendPetConfirmationTokenEmail(String email, PetToken confirmationToken) {
        Context context = new Context();
        context.setVariable("link", "http://localhost:4200/confirmation?token=" + confirmationToken.getToken());

        String body = templateEngine.process("confirmation-token-email-pl", context);
        EmailContentDto emailContentDto = EmailContentDto.anEmailContentDto()
                .receiverAddress(email)
                .subject("Potwierdź dodanie ogłoszenia")
                .content(body)
                .build();
        LOG.info("Sending pet adding confirmation email to: " + email);
        MimeMessage mimeMessage = prepareEmail(emailContentDto);
        javaMailSender.send(mimeMessage);
        LOG.info("Email was successfully sent to: " + email);
    }

    @Override
    public void sendPetDeleteTokenEmail(String email, PetToken deleteToken) {
        Context context = new Context();
        context.setVariable("link", "http://localhost:4200/delete?token=" + deleteToken.getToken());

        String body = templateEngine.process("delete-token-email-pl", context);
        EmailContentDto emailContentDto = EmailContentDto.anEmailContentDto()
                .receiverAddress(email)
                .subject("Twoje ogłoszenie zostało potwierdzone!")
                .content(body)
                .build();
        LOG.info("Sending pet delete token email to: " + email);
        MimeMessage mimeMessage = prepareEmail(emailContentDto);
        javaMailSender.send(mimeMessage);
        LOG.info("Email was successfully sent to: " + email);
    }

    private MimeMessage prepareEmail(EmailContentDto contentDto) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setFrom("Wróć do domu.pl<wrocdodomu@gmail.com>");
            helper.setTo(contentDto.getReceiverAddress());
            helper.setSubject(contentDto.getSubject());
            helper.setText(contentDto.getContent(), true);
            return mail;
        } catch (MessagingException e) {
            LOG.error("Error while creating email content ", e);
            throw new EmailCreationException();
        }
    }
}
