package ru.taf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateBookDTO(
        @NotNull(message = "{books.create.errors.title_is_null")
        @Size(min = 1, max = 50, message = "{books.create.errors.title_size_is_invalid")
        @NotBlank(message = "{books.create.errors.title_is_blank")
        String title,

        @NotNull(message = "{books.create.errors.author_is_null}")
        @Size(min = 1, max = 100, message = "{books.create.errors.author_size_is_invalid")
        @NotBlank(message = "{books.create.errors.author_is_blank}")
        String author,

        @NotNull(message = "{books.create.errors.language_is_null}")
        String language,

        @NotNull(message = "{books.create.errors.publisherDate_is_null}")
        LocalDate publisherDate,

        @NotNull(message = "{books.create.errors.description_is_null}")
        @Size(max = 500, message = "{books.create.errors.description_size_is_invalid}")
        String description
) {}
