package ru.taf.dto;

import java.time.LocalDateTime;

public record DictionaryCardDTO (
        String word,
        String translation,
        String context,
        LocalDateTime createdAt,
        LocalDateTime nextReviewDate,
        int difficulty
) {
}
