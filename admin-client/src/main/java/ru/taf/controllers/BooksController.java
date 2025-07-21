package ru.taf.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.taf.client.BooksRestClient;
import ru.taf.client.exception.BadRequestException;
import ru.taf.dto.BookDTO;
import ru.taf.dto.NewBookDTO;
import ru.taf.services.MinioService;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksRestClient booksClient;

    private final MinioService minioService;

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
    public String create(
            Model model,
            @ModelAttribute("book") NewBookDTO book,
            @RequestParam("coverImage") MultipartFile coverImage,
            @RequestParam("originalFile") MultipartFile originalFile,
            @RequestParam("translatedFile") MultipartFile translatedFile,
            HttpServletResponse response
    ) {
        try {
            String coverImageKey = minioService.uploadFile(coverImage);
            String originalTextKey = minioService.uploadFile(originalFile);
            String translatedTextKey = minioService.uploadFile(translatedFile);

            BookDTO createdBook = booksClient.createBook(book);
            createdBook.setImageUrl(coverImageKey);

            return "redirect:/books/%d".formatted(createdBook.getId());
        } catch (BadRequestException exception) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            model.addAttribute("book", book);
            model.addAttribute("errors", exception.getErrors());
            return "books/new_book";
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            model.addAttribute("book", book);
            model.addAttribute("error", "Ошибка: " + e.getMessage());
            return "books/new_book";
        }
    }
}