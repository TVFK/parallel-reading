package ru.taf.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO (
        @NotNull(message = "email can't be null")
        @NotBlank(message = "email can't be blank")
        @Email(message = "email isn't valid")
        String email,

        @NotNull(message = "password can't be null")
        @NotBlank(message = "password can't be blank")
        @Size(min = 8, max = 55, message = "password length should be between 8 and 55")
        String password
) {
}
