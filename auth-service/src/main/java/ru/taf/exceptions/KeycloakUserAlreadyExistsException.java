package ru.taf.exceptions;

public class KeycloakUserAlreadyExistsException extends RuntimeException{
    public KeycloakUserAlreadyExistsException(String message) {
        super(message);
    }
}
