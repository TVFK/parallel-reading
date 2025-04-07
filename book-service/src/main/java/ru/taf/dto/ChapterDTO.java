package ru.taf.dto;

import ru.taf.entities.Book;

public record ChapterDTO(
        int id,
        int bookId,
        int chapterOrder,
        String title
) {}