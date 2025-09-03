package ru.taf.dto;

import java.io.Serializable;
import java.util.Set;

public record BookDTO(

        int id,
        String title,
        String author,
        String imageKey,
        String level,
        String publishedYear,
        int numberOfPage,
        Set<GenreDTO> genres,
        String description
) implements Serializable {
}
