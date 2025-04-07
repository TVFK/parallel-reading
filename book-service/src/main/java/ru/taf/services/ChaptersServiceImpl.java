package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.taf.entities.Chapter;
import ru.taf.repositories.ChaptersRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChaptersServiceImpl implements ChaptersService {

    private final ChaptersRepository chaptersRepository;

    @Override
    public List<Chapter> getAllChapters() {
        return chaptersRepository.findAll();
    }

    @Override
    public List<Chapter> getAllChapterByBookId(Integer bookId) {
        return chaptersRepository.findAllByBook_Id(bookId); // FIXME Разобраться что за хуйня
    }
}
