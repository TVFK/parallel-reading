package ru.taf.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.taf.dto.UserRegistrationDTO;
import ru.taf.dto.UserUpdateDTO;
import ru.taf.services.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
        authService.registerUser(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid UserUpdateDTO userUpdateDTO){
        authService.updatePassword(userUpdateDTO);
        return ResponseEntity.accepted().build();
    }
}
