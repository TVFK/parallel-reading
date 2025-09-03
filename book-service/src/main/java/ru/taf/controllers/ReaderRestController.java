package ru.taf.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.taf.dto.PageDTO;
import ru.taf.services.PagesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reader")
public class ReaderRestController {

    private final PagesService pagesService;

    @GetMapping("/books/{bookId}")
    public PageDTO getPageByNumberAndBookId(@PathVariable("bookId") Integer bookId,
                                            @RequestParam("pageNumber") int pageNumber) {
        return pagesService.findPageByNumber(bookId, pageNumber);
    }

    @GetMapping("/books/{bookId}/pages")
    public List<PageDTO> getAllPagesByBookId(@PathVariable("bookId") int bookId) {
        return pagesService.findAllPagesByBook(bookId);
    }

    @GetMapping("/chapter-first-page")
    public PageDTO getFirstPageOfChapter(
            @RequestParam("chapterId") Integer chapterId
    ){
        return pagesService.getFirstPage(chapterId);
    }

    @GetMapping("/next")
    public PageDTO getNextPage(@RequestParam("pageId") Integer pageId) {
        return pagesService.getNextPage(pageId);
    }

    @GetMapping("/prev")
    public PageDTO getPrevPage(@RequestParam("pageId") Integer pageId) {
        return pagesService.getPrevPage(pageId);
    }
}
