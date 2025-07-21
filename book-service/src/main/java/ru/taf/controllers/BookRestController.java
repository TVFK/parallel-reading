package ru.taf.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.taf.dto.BookDTO;
import ru.taf.dto.UpdateBookDTO;
import ru.taf.entities.Book;
import ru.taf.services.BooksService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books/{bookId:\\d+}")
public class BookRestController {

    private final BooksService booksService;

    @GetMapping
    public Book findBook(@PathVariable("bookId") Integer bookId) {
        return booksService.getBookById(bookId);
    }

    // TODO защитить, добавить декоратор
    @PatchMapping
    public ResponseEntity<?> updateBook(
            @PathVariable("bookId") Integer bookId,
            @Valid @RequestBody UpdateBookDTO bookDTO,
            BindingResult bindingResult
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        booksService.updateBook(bookId, bookDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBook(
            @PathVariable("bookId") Integer bookId
    ){
        booksService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
