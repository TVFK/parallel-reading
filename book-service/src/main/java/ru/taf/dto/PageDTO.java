package ru.taf.dto;

import ru.taf.entities.Chapter;
import ru.taf.entities.Sentence;

import java.util.List;

public record PageDTO(
        int id,
        int chapterId,
        int pageNumber,
        List<SentenceDTO> sentences
) {
}
