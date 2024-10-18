package ru.taf.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.taf.client.BooksRestClient;
import ru.taf.client.exception.BadRequestException;
import ru.taf.entities.Book;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books/{bookId:\\d+}")
public class BookController {

    private final BooksRestClient booksClient;

    @ModelAttribute("book")
    public Book book(@PathVariable("bookId") int bookId){
        return booksClient.findBook(bookId);
    }

    @GetMapping
    public String getBook(){
        return "/books/book";
    }

    @GetMapping("edit")
    public String getEditPage(){
        return "books/edit";
    }

    @PostMapping("edit")
    public String edit(@PathVariable("bookId") int bookId,
                       @ModelAttribute("book") Book updatedBook,
                       HttpServletResponse response,
                       Model model){
        try {
            booksClient.updateBook(updatedBook, bookId);
            return "redirect:/books/%d".formatted(bookId);
        } catch (BadRequestException exception) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            model.addAttribute("book", updatedBook);
            model.addAttribute("errors", exception.getErrors());
            return "books/edit";
        }
    }

    @PostMapping("delete")
    public String delete(@PathVariable("bookId") int bookId){
        booksClient.deleteBook(bookId);
        return "redirect:/books/list";
    }
}