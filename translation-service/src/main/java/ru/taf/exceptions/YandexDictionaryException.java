package ru.taf.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class YandexDictionaryException extends RuntimeException {

    private final HttpStatusCode httpStatus;
    private final String responseBody;

    public YandexDictionaryException(HttpStatusCode httpStatus, String responseBody) {
        super(responseBody);
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
    }
}
