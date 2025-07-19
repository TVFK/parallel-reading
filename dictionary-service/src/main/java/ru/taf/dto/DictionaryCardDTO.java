package ru.taf.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record DictionaryCardDTO(
        UUID id,
        String word,
        String translation,
        String context,
        LocalDate createdAt,
        LocalDate nextReviewDate,
        int difficulty,
        Set<String> tags
) {
}
