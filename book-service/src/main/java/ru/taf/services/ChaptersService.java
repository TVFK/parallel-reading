package ru.taf.services;

import ru.taf.dto.ChapterDTO;
import ru.taf.entities.Chapter;

import java.util.List;

public interface ChaptersService {

    List<ChapterDTO> getAllChapterByBookId(Integer bookId);

    void create(int bookId, Chapter chapter);
}
