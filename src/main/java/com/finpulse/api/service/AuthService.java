package com.finpulse.api.service;

import com.finpulse.api.dto.AuthResponse;
import com.finpulse.api.dto.LoginRequest;
import com.finpulse.api.dto.RegisterRequest;
import com.finpulse.api.entity.User;
import com.finpulse.api.repository.UserRepository;
import com.finpulse.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .token(jwtService.generateToken(user.getEmail()))
                .build();
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return AuthResponse.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .token(jwtService.generateToken(user.getEmail()))
                .build();
    }
}