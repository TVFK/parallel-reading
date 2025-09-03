package ru.taf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record NewBookDTO(
        @Size(min = 1, max = 50, message = "{books.create.errors.title_size_is_invalid")
        @NotBlank(message = "{books.create.errors.title_is_blank")
        String title,

        @Size(min = 1, max = 100, message = "{books.create.errors.author_size_is_invalid")
        @NotBlank(message = "{books.create.errors.author_is_blank}")
        String author,

        @NotNull(message = "{books.create.errors.language_is_null}")
        String level,

        @NotNull(message = "{books.create.errors.publisherDate_is_null}")
        String publishedYear,

        Set<String> genres,

        String imageKey,

        @NotNull(message = "{books.create.errors.description_is_null}")
        @Size(max = 500, message = "{books.create.errors.description_size_is_invalid}")
        String description
) {
}
