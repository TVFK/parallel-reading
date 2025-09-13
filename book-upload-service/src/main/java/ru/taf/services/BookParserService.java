package ru.taf.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.sentdetect.SentenceDetectorME;
import org.springframework.stereotype.Service;
import ru.taf.dto.BookUploadEvent;
import ru.taf.dto.Chapter;
import ru.taf.dto.Page;
import ru.taf.dto.Sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookParserService {

    private final S3Service s3Service;
    private final SentenceDetectorME englishDetector;
    private final SentenceDetectorME russianDetector;

    public List<Chapter> parseBook(BookUploadEvent event) {
        String originalText = s3Service.getFile(event.originalTextKey());
        String translatedText = s3Service.getFile(event.translatedTextKey());

        List<ChapterContent> originalChapters = splitIntoChapters(originalText);
        List<ChapterContent> translatedChapters = splitIntoChapters(translatedText);

        if (originalChapters.size() != translatedChapters.size()) {
            log.error("Number of chapters in the translation and the original differs. OriginalChapters={}, TranslationChapters={}. OrigChapters={}. TranChapters={}",
                    originalChapters.size(), translatedChapters.size(), originalChapters, translatedChapters);
            throw new RuntimeException("Number of chapters in the translation and the original differs" +
                    originalChapters.size() + " VS " + translatedChapters.size());
        }

        List<Chapter> chapters = new ArrayList<>();
        int totalPages = 0;
        for (int i = 0; i < originalChapters.size(); i++) {
            ChapterContent originalChapter = originalChapters.get(i);
            ChapterContent translatedChapter = translatedChapters.get(i);

            String[] originalSentences = englishDetector.sentDetect(originalChapter.content());
            String[] translatedSentences = russianDetector.sentDetect(translatedChapter.content());

            int minSentences = Math.min(originalSentences.length, translatedSentences.length);
            List<Sentence> sentences = new ArrayList<>();

            for (int j = 0; j < minSentences; j++) {
                sentences.add(new Sentence(j, originalSentences[j], translatedSentences[j]));
            }

            List<Page> pages = createPages(sentences, totalPages + 1);
            totalPages += pages.size();
            chapters.add(new Chapter(originalChapter.title(), i + 1, pages));
        }

        return chapters;
    }

    private List<ChapterContent> splitIntoChapters(String text) {
        List<ChapterContent> chapters = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?im)^(CHAPTER|ГЛАВА)\\s+[IVXLCDM0-9]+.*$");
        Matcher matcher = pattern.matcher(text);

        int previousEnd = 0;
        while (matcher.find()) {
            if (matcher.start() > previousEnd) {
                String content = text.substring(previousEnd, matcher.start()).trim();
                String title = extractChapterTitle(text, previousEnd);
                chapters.add(new ChapterContent(title, content));
            }
            previousEnd = matcher.end();
        }

        if (previousEnd < text.length()) {
            String content = text.substring(previousEnd).trim();
            String title = extractChapterTitle(text, previousEnd);
            chapters.add(new ChapterContent(title, content));
        }

        return chapters;
    }

    private String extractChapterTitle(String text, int position) {
        int lineStart = text.lastIndexOf("\n", position);
        if (lineStart == -1) lineStart = 0;
        else lineStart++;

        int lineEnd = text.indexOf("\n", position);
        if (lineEnd == -1) lineEnd = text.length();

        return text.substring(lineStart, lineEnd).trim();
    }

    private List<Page> createPages(List<Sentence> sentences, int startPageNumber) {
        List<Page> pages = new ArrayList<>();
        int pageSize = 30;

        for (int i = 0; i < sentences.size(); i += pageSize) {
            int end = Math.min(sentences.size(), i + pageSize);
            List<Sentence> pageSentences = sentences.subList(i, end);

            List<Sentence> pageSentencesWithIndex = new ArrayList<>();
            for (int j = 0; j < pageSentences.size(); j++) {
                Sentence original = pageSentences.get(j);
                pageSentencesWithIndex.add(new Sentence(j, original.originalText(), original.translatedText()));
            }

            pages.add(new Page(startPageNumber + pages.size(), pageSentencesWithIndex));
        }

        return pages;
    }

    private record ChapterContent(String title, String content) {
    }
}
