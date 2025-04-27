package ru.taf.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import ru.taf.dto.BookDTO;
import ru.taf.entities.Book;
import ru.taf.mappers.BookMapper;
import ru.taf.services.BooksService;
import ru.taf.specifications.BookSpecifications;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BooksRestController {

    private final BooksService booksService;

    @GetMapping
    public List<BookDTO> getFilteredBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genres,
            @RequestParam(required = false) String level,
            @RequestParam(defaultValue = "publishedYear,asc") String sort) {

        return booksService.getBooks(title, genres, level, sort);
    }


    @GetMapping("grouped-by-levels")
    public List<List<BookDTO>> getBooksGroupByLevel() {
        return booksService.getBooksGroupedByLevel();
    }

    @GetMapping("{title}")
    public BookDTO getBookByTitle(@PathVariable("title") String title){
        return booksService.getBookByTitle(title);
    }
}
