package com.projectv.loginx.service;

import com.projectv.loginx.dto.RegisterRequest;
import com.projectv.loginx.dto.LoginRequest;
import com.projectv.loginx.entity.User;
import com.projectv.loginx.repository.UserRepository;
import com.projectv.loginx.util.PasswordEncoderUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return "Passwords do not match!";
        }

        // Check if email or username already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists!";
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists!";
        }

        // Save new user
        User user = new User();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(PasswordEncoderUtil.encode(request.getPassword()));

        userRepository.save(user);

        return "User registered successfully!";
    }
    public String loginUser(LoginRequest request) {
        Optional<User> checkUser = userRepository.findByEmail(request.getEmail());

        // If not found by email, try username
        if (checkUser.isEmpty()) {
            checkUser = userRepository.findByUsername(request.getUsername());
        }

        // If still not found, return error
        if (checkUser.isEmpty()) {
            return "Username or email not found!";
        }

        User user = checkUser.get();

        // Validate password only for the correct user
        if (!PasswordEncoderUtil.matches(request.getPassword(), user.getPassword())) {
            return "Incorrect password!";
        }

        return "Login successful!";
    }
}
