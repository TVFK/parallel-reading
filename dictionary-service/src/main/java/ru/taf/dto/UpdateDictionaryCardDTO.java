package ru.taf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import java.util.Set;

public record UpdateDictionaryCardDTO(

        @NotNull(message = "word can't be null")
        @Size(min = 1, max = 50, message = "word length must be between 1 and 45")
        @NotBlank(message = "word can't be empty")
        String word,

        @NotNull(message = "translation can't be null")
        @Size(min = 1, max = 50, message = "translation length must be between 1 and 45")
        @NotBlank(message = "translation can't be empty")
        String translation,

        @NotBlank(message = "translation can't be empty")
        String context,

        @Range(min = 1, max = 3, message = "Different must be between 1 and 3")
        int difficulty,

        Set<String> tags
) {
}
