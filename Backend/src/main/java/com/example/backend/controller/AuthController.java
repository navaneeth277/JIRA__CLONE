package com.example.backend.controller;

import com.example.backend.dto.request.*;
import com.example.backend.dto.response.*;
import com.example.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }

    @GetMapping("/me")
    public UserResponse currentUser(
            Authentication authentication) {

        return authService.currentUser(
                authentication.getName()
        );
    }
}