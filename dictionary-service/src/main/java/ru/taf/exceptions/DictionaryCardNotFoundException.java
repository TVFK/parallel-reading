package ru.taf.exceptions;

public class DictionaryCardNotFoundException extends RuntimeException{
    public DictionaryCardNotFoundException(String message) {
        super(message);
    }
}
