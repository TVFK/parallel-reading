package ru.taf.dto;

import java.util.List;

public record Page(
        int pageNumber,
        List<Sentence> sentences
) {
}
