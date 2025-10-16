package com.diaries.repository;

import com.diaries.entity.DiaryEntry;
import com.diaries.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for DiaryEntryRepository.
 * Uses an in-memory H2 database for testing.
 */
@DataJpaTest
class DiaryEntryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DiaryEntryRepository diaryEntryRepository;

    private User testUser;
    private User otherUser;

    @BeforeEach
    void setUp() {
        testUser = new User("test@example.com", "password", "Test User");
        otherUser = new User("other@example.com", "password", "Other User");
        
        entityManager.persist(testUser);
        entityManager.persist(otherUser);
        entityManager.flush();
    }

    @Test
    void testSaveDiaryEntry() {
        DiaryEntry entry = new DiaryEntry("Test Title", "Test Content", LocalDate.now(), testUser);
        
        DiaryEntry saved = diaryEntryRepository.save(entry);
        
        assertNotNull(saved.getId());
        assertEquals("Test Title", saved.getTitle());
        assertEquals("Test Content", saved.getContent());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
    }

    @Test
    void testFindByUserId() {
        DiaryEntry entry1 = new DiaryEntry("Title 1", "Content 1", LocalDate.now(), testUser);
        DiaryEntry entry2 = new DiaryEntry("Title 2", "Content 2", LocalDate.now().minusDays(1), testUser);
        DiaryEntry entry3 = new DiaryEntry("Title 3", "Content 3", LocalDate.now(), otherUser);
        
        entityManager.persist(entry1);
        entityManager.persist(entry2);
        entityManager.persist(entry3);
        entityManager.flush();
        
        Pageable pageable = PageRequest.of(0, 10, Sort.by("entryDate").descending());
        Page<DiaryEntry> entries = diaryEntryRepository.findByUserId(testUser.getId(), pageable);
        
        assertEquals(2, entries.getTotalElements());
        assertTrue(entries.getContent().stream().allMatch(e -> e.getUser().getId().equals(testUser.getId())));
    }

    @Test
    void testSearchEntriesByKeyword() {
        DiaryEntry entry1 = new DiaryEntry("Java Programming", "Learning Java basics", LocalDate.now(), testUser);
        DiaryEntry entry2 = new DiaryEntry("Python Tutorial", "Python is great", LocalDate.now(), testUser);
        DiaryEntry entry3 = new DiaryEntry("JavaScript Guide", "JS fundamentals", LocalDate.now(), testUser);
        
        entityManager.persist(entry1);
        entityManager.persist(entry2);
        entityManager.persist(entry3);
        entityManager.flush();
        
        Pageable pageable = PageRequest.of(0, 10);
        Page<DiaryEntry> results = diaryEntryRepository.searchEntriesByKeyword(testUser.getId(), "java", pageable);
        
        assertEquals(2, results.getTotalElements()); // Should find "Java" and "JavaScript"
    }

    @Test
    void testSearchEntriesWithDateRange() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate twoDaysAgo = today.minusDays(2);
        
        DiaryEntry entry1 = new DiaryEntry("Today", "Content today", today, testUser);
        DiaryEntry entry2 = new DiaryEntry("Yesterday", "Content yesterday", yesterday, testUser);
        DiaryEntry entry3 = new DiaryEntry("Two days ago", "Content old", twoDaysAgo, testUser);
        
        entityManager.persist(entry1);
        entityManager.persist(entry2);
        entityManager.persist(entry3);
        entityManager.flush();
        
        Pageable pageable = PageRequest.of(0, 10);
        Page<DiaryEntry> results = diaryEntryRepository.searchEntries(
            testUser.getId(), "", yesterday, today, pageable
        );
        
        assertEquals(2, results.getTotalElements()); // Should find today and yesterday
    }

    @Test
    void testSearchEntriesWithKeywordAndDateRange() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        
        DiaryEntry entry1 = new DiaryEntry("Java Today", "Learning Java", today, testUser);
        DiaryEntry entry2 = new DiaryEntry("Java Yesterday", "More Java", yesterday, testUser);
        DiaryEntry entry3 = new DiaryEntry("Python Today", "Learning Python", today, testUser);
        
        entityManager.persist(entry1);
        entityManager.persist(entry2);
        entityManager.persist(entry3);
        entityManager.flush();
        
        Pageable pageable = PageRequest.of(0, 10);
        Page<DiaryEntry> results = diaryEntryRepository.searchEntries(
            testUser.getId(), "java", yesterday, today, pageable
        );
        
        assertEquals(2, results.getTotalElements()); // Should find both Java entries
    }

    @Test
    void testFindByUserIdAndEntryDateBetween() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(7);
        LocalDate endDate = today;
        
        DiaryEntry entry1 = new DiaryEntry("Recent", "Content", today, testUser);
        DiaryEntry entry2 = new DiaryEntry("Last Week", "Content", today.minusDays(5), testUser);
        DiaryEntry entry3 = new DiaryEntry("Old", "Content", today.minusDays(10), testUser);
        
        entityManager.persist(entry1);
        entityManager.persist(entry2);
        entityManager.persist(entry3);
        entityManager.flush();
        
        Pageable pageable = PageRequest.of(0, 10);
        Page<DiaryEntry> results = diaryEntryRepository.findByUserIdAndEntryDateBetween(
            testUser.getId(), startDate, endDate, pageable
        );
        
        assertEquals(2, results.getTotalElements());
    }

    @Test
    void testFindByUserIdAndEntryDate() {
        LocalDate today = LocalDate.now();
        
        DiaryEntry entry1 = new DiaryEntry("Entry 1", "Content 1", today, testUser);
        DiaryEntry entry2 = new DiaryEntry("Entry 2", "Content 2", today, testUser);
        DiaryEntry entry3 = new DiaryEntry("Entry 3", "Content 3", today.minusDays(1), testUser);
        
        entityManager.persist(entry1);
        entityManager.persist(entry2);
        entityManager.persist(entry3);
        entityManager.flush();
        
        Pageable pageable = PageRequest.of(0, 10);
        Page<DiaryEntry> results = diaryEntryRepository.findByUserIdAndEntryDate(
            testUser.getId(), today, pageable
        );
        
        assertEquals(2, results.getTotalElements());
    }

    @Test
    void testUserDataIsolation() {
        DiaryEntry entry1 = new DiaryEntry("User 1 Entry", "Content", LocalDate.now(), testUser);
        DiaryEntry entry2 = new DiaryEntry("User 2 Entry", "Content", LocalDate.now(), otherUser);
        
        entityManager.persist(entry1);
        entityManager.persist(entry2);
        entityManager.flush();
        
        Pageable pageable = PageRequest.of(0, 10);
        Page<DiaryEntry> testUserEntries = diaryEntryRepository.findByUserId(testUser.getId(), pageable);
        Page<DiaryEntry> otherUserEntries = diaryEntryRepository.findByUserId(otherUser.getId(), pageable);
        
        assertEquals(1, testUserEntries.getTotalElements());
        assertEquals(1, otherUserEntries.getTotalElements());
        assertNotEquals(testUserEntries.getContent().get(0).getId(), otherUserEntries.getContent().get(0).getId());
    }

    @Test
    void testPagination() {
        for (int i = 0; i < 15; i++) {
            DiaryEntry entry = new DiaryEntry("Entry " + i, "Content " + i, LocalDate.now().minusDays(i), testUser);
            entityManager.persist(entry);
        }
        entityManager.flush();
        
        Pageable page1 = PageRequest.of(0, 10);
        Pageable page2 = PageRequest.of(1, 10);
        
        Page<DiaryEntry> results1 = diaryEntryRepository.findByUserId(testUser.getId(), page1);
        Page<DiaryEntry> results2 = diaryEntryRepository.findByUserId(testUser.getId(), page2);
        
        assertEquals(10, results1.getContent().size());
        assertEquals(5, results2.getContent().size());
        assertEquals(15, results1.getTotalElements());
    }

    @Test
    void testUpdateDiaryEntry() {
        DiaryEntry entry = new DiaryEntry("Original Title", "Original Content", LocalDate.now(), testUser);
        DiaryEntry saved = entityManager.persist(entry);
        entityManager.flush();
        entityManager.clear();
        
        saved.setTitle("Updated Title");
        saved.setContent("Updated Content");
        DiaryEntry updated = diaryEntryRepository.save(saved);
        
        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Updated Content", updated.getContent());
        assertNotNull(updated.getUpdatedAt());
    }

    @Test
    void testDeleteDiaryEntry() {
        DiaryEntry entry = new DiaryEntry("To Delete", "Content", LocalDate.now(), testUser);
        DiaryEntry saved = entityManager.persist(entry);
        entityManager.flush();
        
        diaryEntryRepository.deleteById(saved.getId());
        
        assertFalse(diaryEntryRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void testCascadeDelete() {
        DiaryEntry entry = new DiaryEntry("Entry", "Content", LocalDate.now(), testUser);
        testUser.addDiaryEntry(entry);
        entityManager.persist(entry);
        entityManager.flush();
        
        Long entryId = entry.getId();
        
        entityManager.remove(testUser);
        entityManager.flush();
        
        assertFalse(diaryEntryRepository.findById(entryId).isPresent());
    }
}
