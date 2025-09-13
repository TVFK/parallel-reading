package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.taf.dto.kafka.BookFillingEvent;
import ru.taf.entities.Book;
import ru.taf.entities.Chapter;
import ru.taf.entities.Page;
import ru.taf.entities.Sentence;
import ru.taf.exceptions.BookNotFoundException;
import ru.taf.repositories.BooksRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookFillingEventListener {

    private final ChaptersService chaptersService;
    private final BooksRepository booksRepository;

    @KafkaListener(
            topics = "book-creation-events",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    @Caching(evict = {
            @CacheEvict(value = "booksGroupedByLevel", allEntries = true),
            @CacheEvict(value = "books", allEntries = true)
    })
    public void handleBookUpload(BookFillingEvent event) {
        if (event == null) {
            throw new RuntimeException("Sth went wrong");
        }

        Book book = booksRepository.findById(event.bookId()).orElseThrow(() ->
                new BookNotFoundException("book not found", event.bookId()));

        int numberOfPage = 0;
        for (var chapter : event.chapters()) {
            Chapter newChapter = new Chapter();
            newChapter.setChapterOrder(chapter.chapterOrder());
            newChapter.setTitle(chapter.title());
            List<Page> pages = new ArrayList<>();
            for (var page : chapter.pages()) {
                numberOfPage++;
                Page newPage = new Page();
                newPage.setPageNumber(page.pageNumber());
                newPage.setChapter(newChapter);
                List<Sentence> sentences = new ArrayList<>();
                for (var sentence : page.sentences()) {
                    Sentence newSentence = new Sentence();
                    newSentence.setSentenceIndex(sentence.sentenceIndex());
                    newSentence.setPage(newPage);
                    newSentence.setOriginalText(sentence.originalText());
                    newSentence.setTranslatedText(sentence.translatedText());
                    sentences.add(newSentence);
                }
                newPage.setSentences(sentences);
                pages.add(newPage);
            }
            newChapter.setPages(pages);
            chaptersService.create(event.bookId(), newChapter);
        }
        book.setNumberOfPage(numberOfPage);
        booksRepository.save(book);
    }
}
