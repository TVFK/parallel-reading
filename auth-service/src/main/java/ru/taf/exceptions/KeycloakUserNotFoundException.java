package ru.taf.exceptions;

public class KeycloakUserNotFoundException extends AbstractNotFoundException{
    public KeycloakUserNotFoundException() {
        super("Keycloak user not found", "Operator");
    }
}
