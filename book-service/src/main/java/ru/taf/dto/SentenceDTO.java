package ru.taf.dto;

public record SentenceDTO(
        int sentenceIndex,
        String originalText,
        String translatedText
) {
}
