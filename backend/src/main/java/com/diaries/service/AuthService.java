package com.diaries.service;

import com.diaries.dto.AuthResponse;
import com.diaries.dto.LoginRequest;
import com.diaries.dto.RegisterRequest;
import com.diaries.dto.UserDto;
import com.diaries.entity.User;
import com.diaries.repository.UserRepository;
import com.diaries.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for handling authentication operations.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Register a new user.
     *
     * @param request the registration request
     * @return authentication response with JWT token
     * @throws IllegalArgumentException if email already exists
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());

        // Save user
        user = userRepository.save(user);

        // Generate JWT token
        String token = jwtTokenProvider.generateToken(user.getId());

        // Create user DTO
        UserDto userDto = new UserDto(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getCreatedAt()
        );

        // Return authentication response
        return new AuthResponse(
                token,
                jwtTokenProvider.getExpirationMs(),
                userDto
        );
    }

    /**
     * Authenticate user and generate JWT token.
     *
     * @param request the login request
     * @return authentication response with JWT token
     * @throws BadCredentialsException if credentials are invalid
     */
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Get user from database
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

            // Generate JWT token
            String token = jwtTokenProvider.generateToken(user.getId());

            // Create user DTO
            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getEmail(),
                    user.getFullName(),
                    user.getCreatedAt()
            );

            // Return authentication response
            return new AuthResponse(
                    token,
                    jwtTokenProvider.getExpirationMs(),
                    userDto
            );
        } catch (Exception e) {
            // Don't reveal specific failure reasons for security
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}
