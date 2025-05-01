package ru.taf.exceptions;

import org.springframework.http.HttpStatus;

public class ApiKeyBlockedException extends YandexDictionaryException{
    public ApiKeyBlockedException() {
        super(HttpStatus.PAYMENT_REQUIRED, "The api key was blocked");
    }
}
