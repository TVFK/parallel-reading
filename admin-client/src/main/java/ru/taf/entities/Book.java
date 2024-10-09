package ru.taf.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    private int id;

    private String title;

    private String author;

    private String language;

    private LocalDate publishedDate;

    private String description;

    private List<Chapter> chapters;
}
