package com.diaries.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JwtTokenProvider.
 */
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        // Set test values using reflection
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", 
            "test-secret-key-for-testing-purposes-minimum-256-bits-required-for-hmac-sha");
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", 86400000L); // 24 hours
    }

    @Test
    void testGenerateToken() {
        Long userId = 1L;
        
        String token = jwtTokenProvider.generateToken(userId);
        
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3); // JWT has 3 parts
    }

    @Test
    void testGetUserIdFromToken() {
        Long userId = 123L;
        String token = jwtTokenProvider.generateToken(userId);
        
        Long extractedUserId = jwtTokenProvider.getUserIdFromToken(token);
        
        assertEquals(userId, extractedUserId);
    }

    @Test
    void testValidateToken_ValidToken() {
        Long userId = 1L;
        String token = jwtTokenProvider.generateToken(userId);
        
        boolean isValid = jwtTokenProvider.validateToken(token);
        
        assertTrue(isValid);
    }

    @Test
    void testValidateToken_InvalidToken() {
        String invalidToken = "invalid.token.here";
        
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);
        
        assertFalse(isValid);
    }

    @Test
    void testValidateToken_MalformedToken() {
        String malformedToken = "malformed-token";
        
        boolean isValid = jwtTokenProvider.validateToken(malformedToken);
        
        assertFalse(isValid);
    }

    @Test
    void testValidateToken_EmptyToken() {
        String emptyToken = "";
        
        boolean isValid = jwtTokenProvider.validateToken(emptyToken);
        
        assertFalse(isValid);
    }

    @Test
    void testValidateToken_NullToken() {
        boolean isValid = jwtTokenProvider.validateToken(null);
        
        assertFalse(isValid);
    }

    @Test
    void testGetExpirationMs() {
        long expirationMs = jwtTokenProvider.getExpirationMs();
        
        assertEquals(86400000L, expirationMs);
    }

    @Test
    void testTokenContainsCorrectUserId() {
        Long userId1 = 100L;
        Long userId2 = 200L;
        
        String token1 = jwtTokenProvider.generateToken(userId1);
        String token2 = jwtTokenProvider.generateToken(userId2);
        
        assertEquals(userId1, jwtTokenProvider.getUserIdFromToken(token1));
        assertEquals(userId2, jwtTokenProvider.getUserIdFromToken(token2));
        assertNotEquals(token1, token2);
    }

    @Test
    void testMultipleTokensForSameUser() {
        Long userId = 1L;
        
        String token1 = jwtTokenProvider.generateToken(userId);
        // Delay to ensure different timestamps (JWT uses seconds precision)
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String token2 = jwtTokenProvider.generateToken(userId);
        
        // Tokens should be different (different timestamps)
        assertNotEquals(token1, token2);
        // But both should contain the same user ID
        assertEquals(userId, jwtTokenProvider.getUserIdFromToken(token1));
        assertEquals(userId, jwtTokenProvider.getUserIdFromToken(token2));
    }
}
