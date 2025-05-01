package ru.taf.exceptions;

import org.springframework.http.HttpStatus;

public class DailyLimitExceededException extends YandexDictionaryException{
    public DailyLimitExceededException() {
        super(HttpStatus.FORBIDDEN, "The daily limit on the number of requests has been exceeded");
    }
}
