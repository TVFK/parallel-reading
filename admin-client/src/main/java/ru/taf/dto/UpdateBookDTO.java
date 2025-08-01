package ru.taf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookDTO {
    private String title;

    private String author;

    private String publishedYear;

    private String level;

    private String description;
}
