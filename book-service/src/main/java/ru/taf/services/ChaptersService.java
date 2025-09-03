package ru.taf.services;

import ru.taf.dto.ChapterDTO;
import ru.taf.dto.PageDTO;
import ru.taf.entities.Chapter;

import java.util.List;

public interface ChaptersService {

    List<ChapterDTO> getAllChapterByBookId(Integer bookId);

    void save(int bookId, Chapter chapter);
}
