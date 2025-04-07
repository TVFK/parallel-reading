package ru.taf.services;

import ru.taf.entities.Chapter;

import java.util.List;

public interface ChaptersService {

    List<Chapter> getAllChapters();

    List<Chapter> getAllChapterByBookId(Integer bookId);
}
