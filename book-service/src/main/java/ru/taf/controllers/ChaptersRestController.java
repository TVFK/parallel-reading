package ru.taf.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.taf.dto.ChapterDTO;
import ru.taf.dto.PageDTO;
import ru.taf.repositories.PagesRepository;
import ru.taf.services.ChaptersService;
import ru.taf.services.PagesService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books/{bookId:\\d+}/chapters")
public class ChaptersRestController {

    private final ChaptersService chaptersService;

    @GetMapping
    public List<ChapterDTO> getAllChaptersByBook(@PathVariable("bookId") Integer bookId) {
        return chaptersService.getAllChapterByBookId(bookId);
    }
}