package ru.taf.services.exceptions;

public class BooksNotFoundException extends RuntimeException{
    public BooksNotFoundException(String message) {
        super(message);
    }
}
