package ru.taf.dto.kafka;

import java.util.List;

public record BookFillingEvent(
        int bookId,
        List<Chapter> chapters
) {
}
