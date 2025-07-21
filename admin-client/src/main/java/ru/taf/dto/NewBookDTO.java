package ru.taf.dto;

import java.util.List;

public record NewBookDTO(
        String title,

        String author,

        String level,

        String publishedYear,

        List<String> genres,

        String description,
        String image
) {
}
