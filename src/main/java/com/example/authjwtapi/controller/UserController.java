package com.example.authjwtapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @GetMapping("/api/users/me")
    public Map<String, String> getCurrentUser(Authentication authentication) {

        return Map.of(
                "email", authentication.getName()
        );

    }
}
