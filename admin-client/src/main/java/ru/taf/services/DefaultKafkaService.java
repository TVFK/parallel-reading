package ru.taf.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.taf.dto.BookUploadEvent;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultKafkaService implements KafkaService {

    private final KafkaTemplate<String, BookUploadEvent> kafkaTemplate;
    @Override
    public void sendMessage(String topic, BookUploadEvent event) {
        log.info("send message to kafka. topic={}, event={}", topic, event);
        kafkaTemplate.send(topic, event);
    }
}
