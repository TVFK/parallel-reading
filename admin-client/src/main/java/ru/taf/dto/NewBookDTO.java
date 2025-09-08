package ru.taf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewBookDTO {

    @Size(min = 1, max = 50, message = "{books.create.errors.title_size_is_invalid")
    @NotBlank(message = "{books.create.errors.title_is_blank")
    private String title;

    @Size(min = 1, max = 100, message = "{books.create.errors.author_size_is_invalid")
    @NotBlank(message = "{books.create.errors.author_is_blank}")
    private String author;

    @NotNull(message = "{books.create.errors.language_is_null}")
    private String level;

    private String publishedYear;

    private Set<String> genres;

    @NotNull(message = "{books.create.errors.description_is_null}")
    @Size(max = 500, message = "{books.create.errors.description_size_is_invalid}")
    private String description;

    private String imageKey;
}
