package com.example.authjwtapi.service;

import com.example.authjwtapi.dto.LoginRequest;
import com.example.authjwtapi.dto.LoginResponse;
import com.example.authjwtapi.dto.RegisterRequest;
import com.example.authjwtapi.dto.RegisterResponse;
import com.example.authjwtapi.entity.User;
import com.example.authjwtapi.exception.EmailAlreadyExistsException;
import com.example.authjwtapi.exception.InvalidCredentialsException;
import com.example.authjwtapi.repository.UserRepository;
import com.example.authjwtapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);
        return new RegisterResponse("User registered successfully");
    }
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole());
        return new LoginResponse(token);
    }
}
