package ru.taf.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.taf.dto.BookUploadEvent;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DefaultKafkaService implements KafkaService {

    private final KafkaTemplate<String, BookUploadEvent> kafkaTemplate;
    @Override
    @SneakyThrows
    public void sendMessage(String topic, BookUploadEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
