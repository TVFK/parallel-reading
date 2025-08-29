package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.taf.dto.kafka.BookFillingEvent;
import ru.taf.entities.Chapter;
import ru.taf.entities.Page;
import ru.taf.entities.Sentence;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookFillingEventListener {

    private final ChaptersService chaptersService;

    @KafkaListener(
            topics = "book-processed-events",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleBookUpload(BookFillingEvent event) {
        if(event == null){
            throw new RuntimeException("Sth went wrong");
        }

        for(var chapter : event.chapters()){
            Chapter newChapter = new Chapter();
            newChapter.setChapterOrder(chapter.chapterOrder());
            newChapter.setTitle(chapter.title());
            List<Page> pages = new ArrayList<>();
            for(var page : chapter.pages()){
                Page newPage = new Page();
                newPage.setPageNumber(page.pageNumber());
                newPage.setChapter(newChapter);
                List<Sentence> sentences = new ArrayList<>();
                for(var sentence : page.sentences()){
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
            chaptersService.save(event.bookId(), newChapter);
        }
    }
}
