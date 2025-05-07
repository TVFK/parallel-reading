package ru.taf.exceptions;

public class LemmaProcessingException extends RuntimeException {
    public LemmaProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}