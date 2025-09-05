package ru.taf.dto;

import lombok.NoArgsConstructor;

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
        Integer difficulty,
        Set<String> tags
) {
}
