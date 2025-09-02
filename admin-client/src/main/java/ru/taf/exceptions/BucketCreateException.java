package ru.taf.exceptions;

public class BucketCreateException extends RuntimeException{
    public BucketCreateException(String message) {
        super(message);
    }
}
