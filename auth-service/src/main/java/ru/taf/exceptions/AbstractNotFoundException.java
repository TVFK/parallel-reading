package ru.taf.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class AbstractNotFoundException extends RuntimeException {
    private final String entityName;
    private final String messageKey;

    public AbstractNotFoundException(String messageKey, String entityName) {
        super(messageKey);
        this.messageKey = messageKey;
        this.entityName = entityName;
    }
}