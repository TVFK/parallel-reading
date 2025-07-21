package ru.taf.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDTO {
    private int id;
    private String title;
    private String author;
    private String imageUrl;
    private String level;
    private String publishedYear;
    private int numberOfPage;
    private String description;
    private String image;
}
