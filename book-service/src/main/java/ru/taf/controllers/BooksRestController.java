package ru.taf.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.taf.dto.BookDTO;
import ru.taf.dto.NewBookDTO;
import ru.taf.services.BooksService;

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

    @PostMapping
    public ResponseEntity<?> createDictionaryCard(
            @Valid @RequestBody NewBookDTO newBook,
            BindingResult bindingResult,
            UriComponentsBuilder uriComponentsBuilder
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        BookDTO createdBook = booksService.createBook(newBook);

        // FIXME поменять путь
        return ResponseEntity.created(
                uriComponentsBuilder.path("/books")
                        .build().toUri()
        ).body(createdBook);
    }
}
