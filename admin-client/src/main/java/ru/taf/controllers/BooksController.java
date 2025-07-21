package ru.taf.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.taf.client.BooksRestClient;
import ru.taf.client.exception.BadRequestException;
import ru.taf.dto.BookDTO;
import ru.taf.dto.NewBookDTO;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksRestClient booksClient;

    @GetMapping("list")
    public String getListOfBooks(Model model,
                                 @RequestParam(value = "filter", required = false) String filter) {

        List<BookDTO> books = booksClient.findAllBooks(filter);
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("create")
    public String getCreatePage(Model model) {
        model.addAttribute("book", new NewBookDTO("", "", "", "", new ArrayList<>(), "", ""));
        return "books/new_book";
    }

    @PostMapping("create")
    public String create(Model model,
                         @ModelAttribute("book") NewBookDTO book,
                         HttpServletResponse response) {
        try {
            BookDTO createdBook = booksClient.createBook(book);
            return "redirect:/books/%d".formatted(createdBook.getId());
        } catch (BadRequestException exception) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            model.addAttribute("book", book);
            model.addAttribute("errors", exception.getErrors());
            return "books/new_book";
        }
    }
}