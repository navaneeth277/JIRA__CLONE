package com.example.backend.service;

import com.example.backend.dto.request.*;
import com.example.backend.dto.response.*;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    UserResponse currentUser(String email);
}