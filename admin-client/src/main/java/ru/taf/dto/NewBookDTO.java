package ru.taf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewBookDTO {
    private String title;

    private String author;

    private String level;

    private String publishedYear;

    private Set<String> genres;

    private String description;
    private String coverImageKey;
}
