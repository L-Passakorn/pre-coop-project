package com.diaries.repository;

import com.diaries.entity.DiaryEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * Repository interface for DiaryEntry entity.
 * Provides CRUD operations and custom query methods for diary entry management,
 * including search and filtering capabilities.
 */
@Repository
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Long> {

    /**
     * Find all diary entries for a specific user with pagination support.
     *
     * @param userId   the ID of the user
     * @param pageable pagination information
     * @return a page of diary entries belonging to the user
     */
    Page<DiaryEntry> findByUserId(Long userId, Pageable pageable);

    /**
     * Find all diary entries for a specific user ordered by entry date descending.
     *
     * @param userId   the ID of the user
     * @param pageable pagination information
     * @return a page of diary entries belonging to the user, ordered by entry date (newest first)
     */
    Page<DiaryEntry> findByUserIdOrderByEntryDateDesc(Long userId, Pageable pageable);

    /**
     * Search diary entries by keyword in title or content with date range filtering.
     * The search is case-insensitive and filters by user ID for data isolation.
     *
     * @param userId    the ID of the user
     * @param keyword   the keyword to search for in title and content
     * @param startDate the start date of the range (inclusive)
     * @param endDate   the end date of the range (inclusive)
     * @param pageable  pagination information
     * @return a page of matching diary entries
     */
    @Query("SELECT d FROM DiaryEntry d WHERE d.user.id = :userId " +
           "AND (LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(d.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND d.entryDate BETWEEN :startDate AND :endDate")
    Page<DiaryEntry> searchEntries(
        @Param("userId") Long userId,
        @Param("keyword") String keyword,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        Pageable pageable
    );

    /**
     * Search diary entries by keyword in title or content without date filtering.
     * The search is case-insensitive and filters by user ID for data isolation.
     *
     * @param userId   the ID of the user
     * @param keyword  the keyword to search for in title and content
     * @param pageable pagination information
     * @return a page of matching diary entries
     */
    @Query("SELECT d FROM DiaryEntry d WHERE d.user.id = :userId " +
           "AND (LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(d.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<DiaryEntry> searchEntriesByKeyword(
        @Param("userId") Long userId,
        @Param("keyword") String keyword,
        Pageable pageable
    );

    /**
     * Find diary entries within a date range for a specific user.
     *
     * @param userId    the ID of the user
     * @param startDate the start date of the range (inclusive)
     * @param endDate   the end date of the range (inclusive)
     * @param pageable  pagination information
     * @return a page of diary entries within the date range
     */
    @Query("SELECT d FROM DiaryEntry d WHERE d.user.id = :userId " +
           "AND d.entryDate BETWEEN :startDate AND :endDate")
    Page<DiaryEntry> findByUserIdAndEntryDateBetween(
        @Param("userId") Long userId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        Pageable pageable
    );

    /**
     * Find diary entries for a specific date and user.
     *
     * @param userId    the ID of the user
     * @param entryDate the specific date to search for
     * @param pageable  pagination information
     * @return a page of diary entries for the specified date
     */
    Page<DiaryEntry> findByUserIdAndEntryDate(
        Long userId,
        LocalDate entryDate,
        Pageable pageable
    );
}
