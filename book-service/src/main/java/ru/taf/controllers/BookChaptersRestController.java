package ru.taf.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.taf.dto.ChapterDTO;
import ru.taf.entities.Chapter;
import ru.taf.mappers.ChapterMapper;
import ru.taf.services.ChaptersService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books/{bookId:\\d+}/chapters")
public class BookChaptersRestController {

    private final ChaptersService chaptersService;
    private final ChapterMapper chapterMapper;

    @GetMapping
    public List<ChapterDTO> getAllChaptersByBook(@PathVariable("bookId") Integer bookId) {
        return chaptersService.getAllChapterByBookId(bookId)
                .stream()
                .map(chapterMapper::toDto)
                .collect(Collectors.toList());
    }
}
