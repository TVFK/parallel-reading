package ru.taf.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class AbstractNotFoundException extends RuntimeException {
    private final String entityName;
    private final Object identifier;
    private final String messageKey;
    private final Object[] messageArgs;

    public AbstractNotFoundException(String messageKey, String entityName, Object identifier, Object... messageArgs) {
        super(messageKey);
        this.entityName = entityName;
        this.identifier = identifier;
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }
}