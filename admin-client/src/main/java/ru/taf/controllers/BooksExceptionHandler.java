package ru.taf.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.taf.exceptions.BadRequestException;
import ru.taf.exceptions.BookCreationException;

@ControllerAdvice(assignableTypes = BooksController.class)
public class BooksExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequest(BadRequestException ex, Model model) {
        model.addAttribute("errors", ex.getErrors());
        return "books/new_book";
    }

    @ExceptionHandler(BookCreationException.class)
    public String handleBookCreationException(BookCreationException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "books/new_book";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("error", "Произошла ошибка: " + ex.getMessage());
        return "books/new_book";
    }
}
