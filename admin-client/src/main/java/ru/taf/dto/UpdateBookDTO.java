package ru.taf.dto;


public record UpdateBookDTO (
        String title,

        String author,

        String publisherYear,

        String level,

        String description
) {
}
