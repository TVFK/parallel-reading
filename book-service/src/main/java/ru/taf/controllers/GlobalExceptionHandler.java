package ru.taf.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.taf.exceptions.AbstractNotFoundException;
import ru.taf.exceptions.BookNotFoundException;
import ru.taf.exceptions.PageNotFoundException;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(AbstractNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleAbstractNotFoundException(AbstractNotFoundException ex, Locale locale) {
        String localizedMessage = messageSource.getMessage(ex.getMessage(), ex.getMessageArgs(), locale);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle(ex.getEntityName() + " Not Found");
        problemDetail.setDetail(localizedMessage);
        problemDetail.setProperty("identifier", ex.getIdentifier());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }
}

