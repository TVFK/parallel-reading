package ru.taf.dto;

import java.util.List;

public record BookCreationEvent(
        int bookId,
        List<Chapter> chapters
) {
}
