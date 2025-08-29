package ru.taf.dto;

import ru.taf.dto.Chapter;

import java.util.List;

public record BookFillingEvent(
        int bookId,
        List<Chapter> chapters
) {
}
