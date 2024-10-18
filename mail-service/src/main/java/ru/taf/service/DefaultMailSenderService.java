package ru.taf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.taf.dto.MailParams;

@Service
@RequiredArgsConstructor
public class DefaultMailSenderService implements MailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${service.activation.uri}")
    private String activationServiceUri;

    @Override
    public void send(MailParams mailParams) {
        String subject = "Активация учётной записи";
        String messageBody = getActivationMailBody(mailParams.getId());
        String emailTo = mailParams.getEmailTo();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        mailSender.send(mailMessage);
    }

    private String getActivationMailBody(String id) {
        String msg = String.format("Для заверешения регистрации перейдите по ссылке:\n%s", activationServiceUri);
        return msg.replace("{id}", id);
    }
}
