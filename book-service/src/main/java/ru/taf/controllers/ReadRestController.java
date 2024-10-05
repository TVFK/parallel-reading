package ru.taf.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.taf.entities.Page;
import ru.taf.services.PagesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/read")
public class ReadRestController {

    private final PagesService pagesService;

    @GetMapping("/books{bookId}/pages/{pageNumber}")
    public Page getPage(@PathVariable("bookId") int bookId,
                        @PathVariable("pageNumber") int pageNumber){
        return pagesService.findPageByNumber(bookId, pageNumber);
    }

    @GetMapping("/books/{bookId}/pages")
    public List<Page> getAllPages(@PathVariable("bookId") int bookId){
        return pagesService.findAllPagesByBook(bookId);
    }
}
