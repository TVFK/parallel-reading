package ru.taf.services;

import ru.taf.dto.BookUploadEvent;

public interface KafkaService {
    void sendMessage(String topic, BookUploadEvent event);
}
