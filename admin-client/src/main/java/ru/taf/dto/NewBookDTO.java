package ru.taf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewBookDTO {
    private String title;

    private String author;

    private String level;

    private String publishedYear;

    private List<String> genres;

    private String description;
    private String coverImageKey;
}
