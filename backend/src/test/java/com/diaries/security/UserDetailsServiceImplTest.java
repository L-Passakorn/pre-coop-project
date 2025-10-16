package com.diaries.security;

import com.diaries.entity.User;
import com.diaries.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit tests for UserDetailsServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("test@example.com", "hashedPassword123", "Test User");
        testUser.setId(1L);
    }

    @Test
    void testLoadUserByUsername_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        
        UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");
        
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("hashedPassword123", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent@example.com");
        });
    }

    @Test
    void testLoadUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        
        UserDetails userDetails = userDetailsService.loadUserById(1L);
        
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("hashedPassword123", userDetails.getPassword());
    }

    @Test
    void testLoadUserById_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserById(999L);
        });
    }

    @Test
    void testLoadUserByUsername_WithDifferentEmails() {
        User user1 = new User("user1@example.com", "password1", "User One");
        User user2 = new User("user2@example.com", "password2", "User Two");
        
        when(userRepository.findByEmail("user1@example.com")).thenReturn(Optional.of(user1));
        when(userRepository.findByEmail("user2@example.com")).thenReturn(Optional.of(user2));
        
        UserDetails userDetails1 = userDetailsService.loadUserByUsername("user1@example.com");
        UserDetails userDetails2 = userDetailsService.loadUserByUsername("user2@example.com");
        
        assertEquals("user1@example.com", userDetails1.getUsername());
        assertEquals("user2@example.com", userDetails2.getUsername());
    }

    @Test
    void testUserDetailsHasNoAuthorities() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        
        UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");
        
        assertNotNull(userDetails.getAuthorities());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }
}
