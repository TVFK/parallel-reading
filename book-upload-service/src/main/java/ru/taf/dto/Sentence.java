package ru.taf.dto;

public record Sentence(
        int sentenceIndex,
        String originalText,
        String translatedText
) {
}
