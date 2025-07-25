package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.taf.dto.BookUploadEvent;

@Service
@RequiredArgsConstructor
public class DefaultKafkaService implements KafkaService {

    private final KafkaTemplate<String, BookUploadEvent> kafkaTemplate;
    @Override
    public void sendMessage(String topic, BookUploadEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
