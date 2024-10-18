package ru.taf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.taf.dto.MailParams;
import ru.taf.service.MailSenderService;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailSenderService mailSenderService;

    @PostMapping("/send")
    public ResponseEntity<MailSenderService> sendActivationMail(@RequestBody MailParams mailParams){
        mailSenderService.send(mailParams);
        return ResponseEntity.ok().build();
    }
}
