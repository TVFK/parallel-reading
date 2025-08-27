package ru.taf.exceptions;

public class BookCreationException extends RuntimeException {
    public BookCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}