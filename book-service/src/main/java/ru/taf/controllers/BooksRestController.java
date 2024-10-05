package ru.taf.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.taf.entities.Book;
import ru.taf.services.BooksService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BooksRestController {

    private final BooksService booksService;

    @GetMapping
    public ResponseEntity<List<Book>> findBooks(
            @RequestParam(value = "filter", required = false) String filter) {

        List<Book> books = booksService.findAllBooks(filter);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Book book,
                                    BindingResult bindingResult,
                                    UriComponentsBuilder uriComponentsBuilder)
            throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Book createdBook = booksService.createBook(book);

            return ResponseEntity
                    .created(uriComponentsBuilder
                            .path("/books/{bookId}")
                            .buildAndExpand(createdBook.getId())
                            .toUri())
                    .body(createdBook);
        }
    }
}
