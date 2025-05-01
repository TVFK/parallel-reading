package ru.taf.dto;

import java.io.Serializable;

public record SentenceDTO(
        int sentenceIndex,
        String originalText,
        String translatedText
) implements Serializable {
}
