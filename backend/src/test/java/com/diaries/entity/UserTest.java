package com.diaries.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for User entity.
 */
class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("test@example.com", "hashedPassword123", "Test User");
    }

    @Test
    void testUserCreation() {
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals("hashedPassword123", user.getPassword());
        assertEquals("Test User", user.getFullName());
        assertNotNull(user.getDiaryEntries());
        assertTrue(user.getDiaryEntries().isEmpty());
    }

    @Test
    void testAddDiaryEntry() {
        DiaryEntry entry = new DiaryEntry("Test Title", "Test Content", LocalDate.now(), null);
        
        user.addDiaryEntry(entry);
        
        assertEquals(1, user.getDiaryEntries().size());
        assertTrue(user.getDiaryEntries().contains(entry));
        assertEquals(user, entry.getUser());
    }

    @Test
    void testRemoveDiaryEntry() {
        DiaryEntry entry = new DiaryEntry("Test Title", "Test Content", LocalDate.now(), null);
        user.addDiaryEntry(entry);
        
        user.removeDiaryEntry(entry);
        
        assertEquals(0, user.getDiaryEntries().size());
        assertFalse(user.getDiaryEntries().contains(entry));
        assertNull(entry.getUser());
    }

    @Test
    void testAddMultipleDiaryEntries() {
        DiaryEntry entry1 = new DiaryEntry("Title 1", "Content 1", LocalDate.now(), null);
        DiaryEntry entry2 = new DiaryEntry("Title 2", "Content 2", LocalDate.now().minusDays(1), null);
        
        user.addDiaryEntry(entry1);
        user.addDiaryEntry(entry2);
        
        assertEquals(2, user.getDiaryEntries().size());
        assertEquals(user, entry1.getUser());
        assertEquals(user, entry2.getUser());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User("test@example.com", "password", "Test User");
        user1.setId(1L);
        
        User user2 = new User("test@example.com", "password", "Test User");
        user2.setId(1L);
        
        User user3 = new User("other@example.com", "password", "Other User");
        user3.setId(2L);
        
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1, user3);
    }

    @Test
    void testToString() {
        user.setId(1L);
        String toString = user.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("email='test@example.com'"));
        assertTrue(toString.contains("fullName='Test User'"));
        assertFalse(toString.contains("password")); // Password should not be in toString
    }

    @Test
    void testSettersAndGetters() {
        user.setId(1L);
        user.setEmail("newemail@example.com");
        user.setPassword("newPassword");
        user.setFullName("New Name");
        
        assertEquals(1L, user.getId());
        assertEquals("newemail@example.com", user.getEmail());
        assertEquals("newPassword", user.getPassword());
        assertEquals("New Name", user.getFullName());
    }
}
