package ru.taf.exceptions;

public class BookNotFoundException extends AbstractNotFoundException {
    public BookNotFoundException(String messageKey, Object identifier, Object... messageArgs) {
        super(messageKey, "Book", identifier, messageArgs);
    }
}