package com.diaries.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for authentication DTOs.
 */
class AuthDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // RegisterRequest Tests

    @Test
    void testRegisterRequest_Valid() {
        RegisterRequest request = new RegisterRequest("test@example.com", "password123", "Test User");
        
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        
        assertTrue(violations.isEmpty());
    }

    @Test
    void testRegisterRequest_InvalidEmail() {
        RegisterRequest request = new RegisterRequest("invalid-email", "password123", "Test User");
        
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("valid")));
    }

    @Test
    void testRegisterRequest_EmptyEmail() {
        RegisterRequest request = new RegisterRequest("", "password123", "Test User");
        
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        
        assertFalse(violations.isEmpty());
    }

    @Test
    void testRegisterRequest_ShortPassword() {
        RegisterRequest request = new RegisterRequest("test@example.com", "short", "Test User");
        
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("8 characters")));
    }

    @Test
    void testRegisterRequest_EmptyPassword() {
        RegisterRequest request = new RegisterRequest("test@example.com", "", "Test User");
        
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        
        assertFalse(violations.isEmpty());
    }

    @Test
    void testRegisterRequest_EmptyFullName() {
        RegisterRequest request = new RegisterRequest("test@example.com", "password123", "");
        
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        
        assertFalse(violations.isEmpty());
    }

    @Test
    void testRegisterRequest_ShortFullName() {
        RegisterRequest request = new RegisterRequest("test@example.com", "password123", "A");
        
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        
        assertFalse(violations.isEmpty());
    }

    @Test
    void testRegisterRequest_GettersAndSetters() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setFullName("Test User");
        
        assertEquals("test@example.com", request.getEmail());
        assertEquals("password123", request.getPassword());
        assertEquals("Test User", request.getFullName());
    }

    // LoginRequest Tests

    @Test
    void testLoginRequest_Valid() {
        LoginRequest request = new LoginRequest("test@example.com", "password123");
        
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        
        assertTrue(violations.isEmpty());
    }

    @Test
    void testLoginRequest_InvalidEmail() {
        LoginRequest request = new LoginRequest("invalid-email", "password123");
        
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        
        assertFalse(violations.isEmpty());
    }

    @Test
    void testLoginRequest_EmptyPassword() {
        LoginRequest request = new LoginRequest("test@example.com", "");
        
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        
        assertFalse(violations.isEmpty());
    }

    @Test
    void testLoginRequest_GettersAndSetters() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        
        assertEquals("test@example.com", request.getEmail());
        assertEquals("password123", request.getPassword());
    }

    // UserDto Tests

    @Test
    void testUserDto_Creation() {
        LocalDateTime now = LocalDateTime.now();
        UserDto userDto = new UserDto(1L, "test@example.com", "Test User", now);
        
        assertEquals(1L, userDto.getId());
        assertEquals("test@example.com", userDto.getEmail());
        assertEquals("Test User", userDto.getFullName());
        assertEquals(now, userDto.getCreatedAt());
    }

    @Test
    void testUserDto_GettersAndSetters() {
        UserDto userDto = new UserDto();
        LocalDateTime now = LocalDateTime.now();
        
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setFullName("Test User");
        userDto.setCreatedAt(now);
        
        assertEquals(1L, userDto.getId());
        assertEquals("test@example.com", userDto.getEmail());
        assertEquals("Test User", userDto.getFullName());
        assertEquals(now, userDto.getCreatedAt());
    }

    // AuthResponse Tests

    @Test
    void testAuthResponse_Creation() {
        UserDto userDto = new UserDto(1L, "test@example.com", "Test User", LocalDateTime.now());
        AuthResponse response = new AuthResponse("token123", 86400000L, userDto);
        
        assertEquals("token123", response.getToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals(86400000L, response.getExpiresIn());
        assertEquals(userDto, response.getUser());
    }

    @Test
    void testAuthResponse_GettersAndSetters() {
        AuthResponse response = new AuthResponse();
        UserDto userDto = new UserDto(1L, "test@example.com", "Test User", LocalDateTime.now());
        
        response.setToken("token123");
        response.setTokenType("Bearer");
        response.setExpiresIn(86400000L);
        response.setUser(userDto);
        
        assertEquals("token123", response.getToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals(86400000L, response.getExpiresIn());
        assertEquals(userDto, response.getUser());
    }

    @Test
    void testAuthResponse_DefaultTokenType() {
        AuthResponse response = new AuthResponse();
        
        assertEquals("Bearer", response.getTokenType());
    }
}
