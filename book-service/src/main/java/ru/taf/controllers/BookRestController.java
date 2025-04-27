package ru.taf.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.taf.dto.BookDTO;
import ru.taf.entities.Book;
import ru.taf.services.BooksService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books/{bookId:\\d+}")
public class BookRestController {

    private final BooksService booksService;

    @ModelAttribute
    public Book getBook(@PathVariable("bookId") Integer bookId) {
        return booksService.getBookById(bookId);
    }

    @GetMapping
    public Book findBook(@ModelAttribute("book") Book book) {
        return book;
    }
}
