package ru.taf.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.taf.dto.BookUploadEvent;

@Service
public class BookUploadEventListener {
    @KafkaListener(
            topics = "book-processed-events",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleBookUpload(BookUploadEvent event) {
        System.out.println("Received BookUploadEvent: {}" + event);
    }
}
