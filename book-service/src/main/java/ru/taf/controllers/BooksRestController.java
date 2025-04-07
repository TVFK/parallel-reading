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
    private final BookMapper bookMapper;

    @GetMapping
    public List<BookDTO> getFilteredBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genres,
            @RequestParam(required = false) String level,
            @RequestParam(defaultValue = "publishedYear,asc") String sort) {

        Specification<Book> spec = BookSpecifications.withFilters(title, genres, level);
        Sort sortOrder = Sort.by(Sort.Order.asc("publishedYear"));

        if (sort != null) {
            String[] sortParams = sort.split(",");
            if (sortParams.length == 2) {
                sortOrder = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
            }
        }

        return booksService.getBooks(spec, sortOrder)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }


    @GetMapping("grouped-by-levels")
    public List<List<BookDTO>> getBooksGroupByLevel() {
        return booksService.getBooksGroupedByLevel()
                .stream()
                .map(booksGroup -> booksGroup
                        .stream()
                        .map(bookMapper::toDto)
                        .toList())
                .toList();
    }

    @GetMapping("{title}")
    public BookDTO getBookByTitle(@PathVariable("title") String title){
        return bookMapper.toDto(booksService.getBookByTitle(title));
    }
}
