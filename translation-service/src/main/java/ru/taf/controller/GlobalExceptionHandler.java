package ru.taf.controller;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.taf.exceptions.YandexDictionaryException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(YandexDictionaryException.class)
    public ResponseEntity<ProblemDetail> handleYandexDictException(YandexDictionaryException ex){

        ProblemDetail problemDetail = ProblemDetail.forStatus(ex.getHttpStatus());
        problemDetail.setDetail(ex.getResponseBody());

        return ResponseEntity.status(ex.getHttpStatus()).body(problemDetail);
    }
}
