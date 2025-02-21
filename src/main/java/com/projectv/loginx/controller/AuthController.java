package com.projectv.loginx.controller;

import com.projectv.loginx.dto.RegisterRequest;
import com.projectv.loginx.dto.LoginRequest;
import com.projectv.loginx.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.loginUser(request);
    }
}

