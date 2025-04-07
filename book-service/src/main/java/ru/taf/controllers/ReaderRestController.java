package ru.taf.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.taf.dto.PageDTO;
import ru.taf.mappers.PageMapper;
import ru.taf.services.PagesService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/read")
public class ReaderRestController {

    private final PagesService pagesService;
    private final PageMapper pageMapper;

    @GetMapping("/books/{bookId}")
    public PageDTO getPageByNumberAndBookId(@PathVariable("bookId") Integer bookId,
                                               @RequestParam("pageNumber") int pageNumber) {
        return pageMapper.toDto(pagesService.findPageByNumber(bookId, pageNumber));
    }

    @GetMapping("/books/{bookId}/pages")
    public List<PageDTO> getAllPagesByBookId(@PathVariable("bookId") int bookId) {
        return pagesService.findAllPagesByBook(bookId)
                .stream()
                .map(pageMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/next")
    public PageDTO getNextPage(@RequestParam("pageId") Integer pageId) {
        return pageMapper.toDto(pagesService.getNextPage(pageId));
    }

    @GetMapping("/prev")
    public PageDTO getPrevPage(@RequestParam("pageId") Integer pageId) {
        return pageMapper.toDto(pagesService.getPrevPage(pageId));
    }
}
