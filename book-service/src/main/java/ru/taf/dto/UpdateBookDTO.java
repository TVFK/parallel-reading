package ru.taf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateBookDTO(
        @NotNull(message = "{books.update.errors.title_is_null}")
        @Size(min = 3, max = 100, message = "{books.update.errors.title_size_is_invalid}")
        @NotBlank(message = "{books.update.errors.title_is_blank}")
        String title,

        @NotNull(message = "{books.update.errors.author_is_null}")
        @Size(min = 3, max = 55, message = "{books.update.errors.author_size_is_invalid}")
        @NotBlank(message = "{books.update.errors.author_is_blank}")
        String author,

        @NotNull(message = "{books.update.errors.publisherDate_is_null}")
        String publishedYear,

        @NotNull(message = "level can't be null")
        String level,

        @NotNull(message = "{books.update.errors.description_is_null}")
        @Size(max = 500, message = "books.update.errors.description_size_is_invalid")
        String description
) {}
