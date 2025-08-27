package ru.taf.exceptions;

public class KeycloakUserCreationException extends RuntimeException{
    public KeycloakUserCreationException(String message) {
        super(message);
    }
}
