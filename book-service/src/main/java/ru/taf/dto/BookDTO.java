package ru.taf.dto;

import lombok.Builder;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Builder
public record BookDTO(

        int id,
        String title,
        String author,
        String imageKey,
        String publishedYear,
        String level,
        String description,
        int numberOfPage,
        Set<GenreDTO> genres
) implements Serializable {
}
