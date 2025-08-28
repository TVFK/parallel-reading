package ru.taf.services;

import lombok.RequiredArgsConstructor;
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
            throw new RuntimeException("Количество глав в оригинале и переводе не совпадает");
        }

        List<Chapter> chapters = new ArrayList<>();
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

            List<Page> pages = createPages(sentences);
            chapters.add(new Chapter(originalChapter.title(), i + 1, pages));
        }

        return chapters;
    }

    private List<ChapterContent> splitIntoChapters(String text) {
        List<ChapterContent> chapters = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?i)^CHAPTER\\s+[IVXLCDM0-9]+.*$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text);

        int start = 0;
        while (matcher.find()) {
            if (start != 0) {
                String content = text.substring(start, matcher.start()).trim();
                chapters.add(new ChapterContent(extractChapterTitle(text, start), content));
            }
            start = matcher.start();
        }

        if (start < text.length()) {
            String content = text.substring(start).trim();
            chapters.add(new ChapterContent(extractChapterTitle(text, start), content));
        }

        return chapters;
    }

    private String extractChapterTitle(String text, int start) {
        int end = text.indexOf("\n", start);
        if (end == -1) end = text.length();
        return text.substring(start, end).trim();
    }

    private List<Page> createPages(List<Sentence> sentences) {
        List<Page> pages = new ArrayList<>();
        int pageSize = 10;

        for (int i = 0; i < sentences.size(); i += pageSize) {
            int end = Math.min(sentences.size(), i + pageSize);
            List<Sentence> pageSentences = sentences.subList(i, end);

            List<Sentence> pageSentencesWithIndex = new ArrayList<>();
            for (int j = 0; j < pageSentences.size(); j++) {
                Sentence original = pageSentences.get(j);
                pageSentencesWithIndex.add(new Sentence(j, original.originalText(), original.translatedText()));
            }

            pages.add(new Page(pages.size() + 1, pageSentencesWithIndex));
        }

        return pages;
    }

    private record ChapterContent(String title, String content) {
    }
}
