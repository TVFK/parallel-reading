package ru.taf.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.dto.ChapterDTO;
import ru.taf.entities.Chapter;
import ru.taf.mappers.ChapterMapper;
import ru.taf.repositories.ChaptersRepository;
import ru.taf.services.ChaptersService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChaptersServiceImpl implements ChaptersService {

    private final ChaptersRepository chaptersRepository;
    private final ChapterMapper chapterMapper;

    @Cacheable(value = "ChapterService::getAllChaptersByBookId", key = "#bookId")
    @Override
    public List<ChapterDTO> getAllChapterByBookId(Integer bookId) {
        List<Chapter> allByBookId = chaptersRepository.findAllByBook_Id(bookId);

        return allByBookId.stream()
                .map(chapterMapper::toDto)
                .collect(Collectors.toList());
    }
}
