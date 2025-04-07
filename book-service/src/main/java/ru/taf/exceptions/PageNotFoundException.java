package ru.taf.exceptions;

public class PageNotFoundException extends AbstractNotFoundException {
    public PageNotFoundException(String messageKey, Object identifier, Object... messageArgs) {
        super(messageKey, "Page", identifier, messageArgs);
    }
}
