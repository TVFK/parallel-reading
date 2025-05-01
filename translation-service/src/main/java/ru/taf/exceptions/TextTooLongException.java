package ru.taf.exceptions;

import org.springframework.http.HttpStatus;

public class TextTooLongException extends YandexDictionaryException{
    public TextTooLongException() {
        super(HttpStatus.PAYLOAD_TOO_LARGE, "The maximum text size has been exceeded");
    }
}
