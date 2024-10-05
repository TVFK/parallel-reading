package ru.taf.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.taf.entities.Book;
import ru.taf.services.BooksService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books/{bookId:\\d+}")
public class BookRestController {

    private final BooksService booksService;

    @ModelAttribute
    public Book getBook(@PathVariable("bookId") int bookId){
        return booksService.findProduct(bookId);
    }

    @GetMapping
    public Book findBook(@ModelAttribute("book") Book book){
        return book;
    }

    @PatchMapping
    public ResponseEntity<?> updateBook(@PathVariable("bookId") int bookId,
                                        @Valid @RequestBody Book book,
                                        BindingResult bindingResult)
            throws BindException {

        if(bindingResult.hasErrors()){
            if(bindingResult instanceof BindException exception){
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            booksService.updateBook(bookId, book);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBook(@PathVariable("bookId") int bookId){
        booksService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
