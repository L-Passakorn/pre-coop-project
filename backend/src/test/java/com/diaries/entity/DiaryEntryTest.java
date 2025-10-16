package com.diaries.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DiaryEntry entity.
 */
class DiaryEntryTest {

    private DiaryEntry diaryEntry;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("test@example.com", "password", "Test User");
        user.setId(1L);
        diaryEntry = new DiaryEntry("Test Title", "Test Content", LocalDate.now(), user);
    }

    @Test
    void testDiaryEntryCreation() {
        assertNotNull(diaryEntry);
        assertEquals("Test Title", diaryEntry.getTitle());
        assertEquals("Test Content", diaryEntry.getContent());
        assertEquals(LocalDate.now(), diaryEntry.getEntryDate());
        assertEquals(user, diaryEntry.getUser());
    }

    @Test
    void testSettersAndGetters() {
        LocalDate newDate = LocalDate.of(2024, 1, 15);
        User newUser = new User("new@example.com", "password", "New User");
        newUser.setId(2L);
        
        diaryEntry.setId(1L);
        diaryEntry.setTitle("New Title");
        diaryEntry.setContent("New Content");
        diaryEntry.setEntryDate(newDate);
        diaryEntry.setUser(newUser);
        
        assertEquals(1L, diaryEntry.getId());
        assertEquals("New Title", diaryEntry.getTitle());
        assertEquals("New Content", diaryEntry.getContent());
        assertEquals(newDate, diaryEntry.getEntryDate());
        assertEquals(newUser, diaryEntry.getUser());
    }

    @Test
    void testEqualsAndHashCode() {
        DiaryEntry entry1 = new DiaryEntry("Title", "Content", LocalDate.now(), user);
        entry1.setId(1L);
        
        DiaryEntry entry2 = new DiaryEntry("Title", "Content", LocalDate.now(), user);
        entry2.setId(1L);
        
        DiaryEntry entry3 = new DiaryEntry("Other Title", "Other Content", LocalDate.now(), user);
        entry3.setId(2L);
        
        assertEquals(entry1, entry2);
        assertEquals(entry1.hashCode(), entry2.hashCode());
        assertNotEquals(entry1, entry3);
    }

    @Test
    void testToString() {
        diaryEntry.setId(1L);
        String toString = diaryEntry.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("title='Test Title'"));
        assertTrue(toString.contains("entryDate=" + LocalDate.now()));
        assertFalse(toString.contains("content")); // Content should not be in toString for brevity
    }

    @Test
    void testBidirectionalRelationship() {
        User testUser = new User("test@example.com", "password", "Test User");
        DiaryEntry entry = new DiaryEntry("Title", "Content", LocalDate.now(), null);
        
        testUser.addDiaryEntry(entry);
        
        assertEquals(testUser, entry.getUser());
        assertTrue(testUser.getDiaryEntries().contains(entry));
    }

    @Test
    void testLongContent() {
        String longContent = "A".repeat(10000); // Test with very long content
        diaryEntry.setContent(longContent);
        
        assertEquals(longContent, diaryEntry.getContent());
        assertEquals(10000, diaryEntry.getContent().length());
    }

    @Test
    void testDifferentDates() {
        LocalDate pastDate = LocalDate.of(2020, 1, 1);
        LocalDate futureDate = LocalDate.of(2030, 12, 31);
        
        diaryEntry.setEntryDate(pastDate);
        assertEquals(pastDate, diaryEntry.getEntryDate());
        
        diaryEntry.setEntryDate(futureDate);
        assertEquals(futureDate, diaryEntry.getEntryDate());
    }
}
