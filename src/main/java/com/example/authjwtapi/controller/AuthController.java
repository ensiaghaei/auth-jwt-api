package com.example.authjwtapi.controller;

import com.example.authjwtapi.dto.LoginRequest;
import com.example.authjwtapi.dto.LoginResponse;
import com.example.authjwtapi.dto.RegisterRequest;
import com.example.authjwtapi.dto.RegisterResponse;
import com.example.authjwtapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
