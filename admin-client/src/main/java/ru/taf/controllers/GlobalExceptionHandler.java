package ru.taf.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.taf.exceptions.AuthorizationException;
import ru.taf.exceptions.FileUploadException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail("Request has errors");
        problemDetail.setProperty("errors",
                ex.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList());

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ProblemDetail> handleFileUploadException(FileUploadException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail("Failed to upload file");
        problemDetail.setProperty("error", ex.getMessage());

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ProblemDetail> handleAuthorizationException(AuthorizationException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setDetail("User unauthorized");
        problemDetail.setProperty("error", ex.getMessage());

        return ResponseEntity.badRequest().body(problemDetail);
    }
}
