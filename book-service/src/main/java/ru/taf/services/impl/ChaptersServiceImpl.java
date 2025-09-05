package ru.taf.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.dto.ChapterDTO;
import ru.taf.dto.PageDTO;
import ru.taf.entities.Book;
import ru.taf.dto.mappers.ChapterMapper;
import ru.taf.entities.Chapter;
import ru.taf.entities.Page;
import ru.taf.exceptions.BookNotFoundException;
import ru.taf.exceptions.PageNotFoundException;
import ru.taf.repositories.BooksRepository;
import ru.taf.repositories.ChaptersRepository;
import ru.taf.repositories.PagesRepository;
import ru.taf.services.ChaptersService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChaptersServiceImpl implements ChaptersService {

    private final ChaptersRepository chaptersRepository;
    private final ChapterMapper chapterMapper;
    private final BooksRepository booksRepository;

    @Cacheable(value = "ChapterService::getAllChaptersByBookId", key = "#bookId")
    @Override
    public List<ChapterDTO> getAllChapterByBookId(Integer bookId) {
        List<Chapter> allByBookId = chaptersRepository.findAllByBook_Id(bookId);

        return allByBookId.stream()
                .map(chapterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void create(int bookId, Chapter chapter) {
        Book book = booksRepository.findById(bookId).orElseThrow(() ->
                new BookNotFoundException("book.not_found", bookId));

        chapter.setBook(book);

        chaptersRepository.save(chapter);
    }
}
