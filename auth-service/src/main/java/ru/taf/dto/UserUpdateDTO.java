package ru.taf.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO (
        @NotBlank(message = "email can't be blank")
        @Email(message = "email isn't valid")
        String email
){
}
