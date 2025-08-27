package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import ru.taf.dto.UserRegistrationDTO;
import ru.taf.dto.UserUpdateDTO;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IdentityProviderClient identityProviderClient;

    public void registerUser(UserRegistrationDTO userRegistrationDTO){
        identityProviderClient.createUser(userRegistrationDTO.email(), userRegistrationDTO.password());
    }

    public void updatePassword(UserUpdateDTO userUpdateDTO){
        identityProviderClient.resetPassword(userUpdateDTO.email());
    }
}
