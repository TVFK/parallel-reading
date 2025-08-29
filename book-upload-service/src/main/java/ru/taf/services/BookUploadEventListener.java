package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.taf.dto.BookFillingEvent;
import ru.taf.dto.BookUploadEvent;
import ru.taf.dto.Chapter;

import java.util.List;

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
            throw new RuntimeException("ОШИБКА ПРИ ПАРСИНГЕ ТЕКСТА КНИГИ: " + e.getMessage(), e);
        }
    }

    public void sendMessage(int bookId, List<Chapter> chapters) {
        BookFillingEvent creationEvent = new BookFillingEvent(bookId, chapters);
        kafkaTemplate.send("book-creation-events", creationEvent);
    }
}
