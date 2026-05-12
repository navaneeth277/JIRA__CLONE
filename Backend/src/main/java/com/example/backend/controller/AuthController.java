package com.example.backend.controller;// tells package name

// import all this packages
import com.example.backend.dto.request.*;
import com.example.backend.dto.response.*;
import com.example.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController// this handles http request and responds and container tell to create a container to spring boot
@RequestMapping("/api/auth")//url path
@RequiredArgsConstructor// lambok annootation
public class AuthController {

    private final AuthService authService;//only accases in this class-> used private

    @PostMapping("/register")//post end point..
    public AuthResponse register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);//calls authservics.
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