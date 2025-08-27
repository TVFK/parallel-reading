package ru.taf.services;

public interface IdentityProviderClient {
    void createUser(String email, String password);
    void resetPassword(String userId);
}
