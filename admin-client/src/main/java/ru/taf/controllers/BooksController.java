package ru.taf.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.taf.client.BooksRestClient;
import ru.taf.exceptions.BadRequestException;
import ru.taf.dto.BookDTO;
import ru.taf.dto.NewBookDTO;
import ru.taf.services.BooksService;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksRestClient booksClient;

    private final BooksService booksService;

    @GetMapping("list")
    public String getListOfBooks(Model model,
                                 @RequestParam(value = "filter", required = false) String filter) {

        List<BookDTO> books = booksClient.findAllBooks(filter);
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("create")
    public String getCreatePage(Model model) {
        model.addAttribute("book", new NewBookDTO());
        return "books/new_book";
    }

    @PostMapping("create")
    public String createBook(
            @ModelAttribute("book") @Valid NewBookDTO book,
            @RequestParam("coverImage") MultipartFile coverImage,
            @RequestParam("originalFile") MultipartFile originalFile,
            @RequestParam("translatedFile") MultipartFile translatedFile
    ) {
        BookDTO createdBook = booksService.createBook(book, coverImage, originalFile, translatedFile);
        return "redirect:/books/%d".formatted(createdBook.getId());
    }
}