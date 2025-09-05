package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.taf.client.BooksRestClient;
import ru.taf.dto.BookDTO;
import ru.taf.dto.BookUploadEvent;
import ru.taf.dto.NewBookDTO;
import ru.taf.exceptions.BookCreationException;

@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRestClient booksClient;

    private final S3Service minioService;

    private final KafkaService kafkaService;

    public BookDTO createBook(
            NewBookDTO book,
            MultipartFile coverImage,
            MultipartFile originalFile,
            MultipartFile translatedFile
    ) {
        try {
            String coverImageKey = minioService.uploadCover(coverImage);
            String originalTextKey = minioService.uploadText(originalFile);
            String translatedTextKey = minioService.uploadText(translatedFile);

            book.setImageKey(coverImageKey);
            BookDTO createdBook = booksClient.createBook(book);

            BookUploadEvent bookUploadEvent = new BookUploadEvent(createdBook.getId(), originalTextKey, translatedTextKey);
            kafkaService.sendMessage("book-processed-events", bookUploadEvent);

            return createdBook;
        } catch (Exception e) {
            throw new BookCreationException("Error when creating book: " + e.getMessage(), e);
        }
    }
}
