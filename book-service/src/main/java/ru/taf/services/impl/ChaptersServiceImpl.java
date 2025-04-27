package ru.taf.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.entities.Chapter;
import ru.taf.repositories.ChaptersRepository;
import ru.taf.services.ChaptersService;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChaptersServiceImpl implements ChaptersService {

    private final ChaptersRepository chaptersRepository;

    @Cacheable(value = "ChapterService::getAllChapters")
    @Override
    public List<Chapter> getAllChapters() {
        return chaptersRepository.findAll();
    }

    @Cacheable(value = "ChapterService::getAllChaptersByBookId", key = "#bookId")
    @Override
    public List<Chapter> getAllChapterByBookId(Integer bookId) {
        return chaptersRepository.findAllByBook_Id(bookId); // FIXME Разобраться
    }
}
