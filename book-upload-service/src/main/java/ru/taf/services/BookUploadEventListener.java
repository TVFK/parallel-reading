package ru.taf.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.dto.BookFillingEvent;
import ru.taf.dto.BookUploadEvent;
import ru.taf.dto.Chapter;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookUploadEventListener {

    private final BookParserService bookParserService;

    private final KafkaTemplate<String, BookFillingEvent> kafkaTemplate;

    @KafkaListener(
            topics = "book-processed-events",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleBookUpload(BookUploadEvent event) {
        try {
            List<Chapter> chapters = bookParserService.parseBook(event);
            sendMessage(event.bookId(), chapters);
        } catch (Exception e) {
            log.error("Error with creation book. Event={}", event, e);
            throw new RuntimeException("Error with creation book: " + e.getMessage(), e);
        }
    }

    public void sendMessage(int bookId, List<Chapter> chapters) {
        BookFillingEvent creationEvent = new BookFillingEvent(bookId, chapters);
        kafkaTemplate.send("book-creation-events", creationEvent);
    }
}
