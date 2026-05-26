package com.finpulse.api;

import com.finpulse.api.dto.AuthResponse;
import com.finpulse.api.dto.LoginRequest;
import com.finpulse.api.dto.RegisterRequest;
import com.finpulse.api.entity.User;
import com.finpulse.api.repository.UserRepository;
import com.finpulse.api.security.JwtService;
import com.finpulse.api.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User savedUser;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setFullName("Peter Obi");
        registerRequest.setEmail("peter@gmail.com");
        registerRequest.setPassword("password123");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("peter@gmail.com");
        loginRequest.setPassword("password123");

        savedUser = User.builder()
                .id(1L)
                .fullName("Peter Obi")
                .email("peter@gmail.com")
                .password("$2a$10$hashedpassword")
                .build();
    }

    // ─── REGISTER TESTS ───────────────────────────────────────

    @Test
    void register_ShouldReturnAuthResponse_WhenEmailIsNew() {
        // Arrange
        when(userRepository.existsByEmail("peter@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$hashedpassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken("peter@gmail.com")).thenReturn("fake.jwt.token");

        // Act
        AuthResponse response = authService.register(registerRequest);

        // Assert
        assertThat(response.getEmail()).isEqualTo("peter@gmail.com");
        assertThat(response.getFullName()).isEqualTo("Peter Obi");
        assertThat(response.getToken()).isEqualTo("fake.jwt.token");
    }

    @Test
    void register_ShouldThrowException_WhenEmailAlreadyExists() {
        // Arrange
        when(userRepository.existsByEmail("peter@gmail.com")).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> authService.register(registerRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Email already in use");

        verify(userRepository, never()).save(any(User.class));
    }

    // ─── LOGIN TESTS ───────────────────────────────────────────

    @Test
    void login_ShouldReturnAuthResponse_WhenCredentialsAreValid() {
        // Arrange
        when(userRepository.findByEmail("peter@gmail.com"))
                .thenReturn(Optional.of(savedUser));
        when(passwordEncoder.matches("password123", "$2a$10$hashedpassword"))
                .thenReturn(true);
        when(jwtService.generateToken("peter@gmail.com")).thenReturn("fake.jwt.token");

        // Act
        AuthResponse response = authService.login(loginRequest);

        // Assert
        assertThat(response.getEmail()).isEqualTo("peter@gmail.com");
        assertThat(response.getToken()).isEqualTo("fake.jwt.token");
    }

    @Test
    void login_ShouldThrowException_WhenEmailNotFound() {
        // Arrange
        when(userRepository.findByEmail("peter@gmail.com"))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void login_ShouldThrowException_WhenPasswordIsWrong() {
        // Arrange
        when(userRepository.findByEmail("peter@gmail.com"))
                .thenReturn(Optional.of(savedUser));
        when(passwordEncoder.matches("password123", "$2a$10$hashedpassword"))
                .thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid email or password");
    }
}