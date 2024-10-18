package ru.taf.service;

import ru.taf.dto.MailParams;

public interface MailSenderService {
    void send(MailParams mailParams);
}
