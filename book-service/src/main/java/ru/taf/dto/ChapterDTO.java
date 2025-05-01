package ru.taf.dto;

import ru.taf.entities.Book;

import java.io.Serializable;

public record ChapterDTO(
        int id,
        int bookId,
        int chapterOrder,
        String title
) implements Serializable {}