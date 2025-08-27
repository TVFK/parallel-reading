package ru.taf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookDTO {

    @NotNull(message = "{books.update.errors.title_is_null}")
    @Size(min = 3, max = 100, message = "{books.update.errors.title_size_is_invalid}")
    @NotBlank(message = "{books.update.errors.title_is_blank}")
    private String title;

    @NotNull(message = "{books.update.errors.author_is_null}")
    @Size(min = 3, max = 55, message = "{books.update.errors.author_size_is_invalid}")
    @NotBlank(message = "{books.update.errors.author_is_blank}")
    private String author;

    @NotNull(message = "{books.update.errors.publisherDate_is_null}")
    private String publishedYear;

    @NotNull(message = "level can't be null")
    private String level;

    @NotNull(message = "{books.update.errors.description_is_null}")
    @Size(max = 500, message = "books.update.errors.description_size_is_invalid")
    private String description;
}
