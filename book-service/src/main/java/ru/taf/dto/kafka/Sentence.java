package ru.taf.dto.kafka;

public record Sentence(
        int sentenceIndex,
        String originalText,
        String translatedText
) {
}
