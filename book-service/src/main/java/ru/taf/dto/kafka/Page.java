package ru.taf.dto.kafka;

import java.util.List;

public record Page(
        int pageNumber,
        List<Sentence> sentences
) {
}
