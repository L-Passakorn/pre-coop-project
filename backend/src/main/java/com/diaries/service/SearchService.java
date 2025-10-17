package com.diaries.service;

import com.diaries.dto.DiaryEntryDto;
import com.diaries.entity.DiaryEntry;
import com.diaries.repository.DiaryEntryRepository;
import com.diaries.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Service for searching and filtering diary entries.
 */
@Service
public class SearchService {

    private final DiaryEntryRepository diaryEntryRepository;
    private final UserRepository userRepository;

    public SearchService(DiaryEntryRepository diaryEntryRepository, UserRepository userRepository) {
        this.diaryEntryRepository = diaryEntryRepository;
        this.userRepository = userRepository;
    }

    /**
     * Search diary entries with optional filters.
     * Supports keyword search, date range filtering, and specific date filtering.
     *
     * @param email     the authenticated user's email
     * @param keyword   optional keyword to search in title and content
     * @param startDate optional start date for date range filter
     * @param endDate   optional end date for date range filter
     * @param date      optional specific date filter
     * @param pageable  pagination information
     * @return page of matching diary entry DTOs
     */
    @Transactional(readOnly = true)
    public Page<DiaryEntryDto> search(
            String email,
            String keyword,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate date,
            Pageable pageable) {

        // Get user ID from email
        Long userId = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"))
                .getId();

        // If specific date is provided, search for that date only
        if (date != null) {
            return diaryEntryRepository.findByUserIdAndEntryDate(userId, date, pageable)
                    .map(this::toDto);
        }

        // If keyword and date range are provided
        if (keyword != null && !keyword.trim().isEmpty() && startDate != null && endDate != null) {
            return diaryEntryRepository.searchEntries(userId, keyword.trim(), startDate, endDate, pageable)
                    .map(this::toDto);
        }

        // If only keyword is provided
        if (keyword != null && !keyword.trim().isEmpty()) {
            return diaryEntryRepository.searchEntriesByKeyword(userId, keyword.trim(), pageable)
                    .map(this::toDto);
        }

        // If only date range is provided
        if (startDate != null && endDate != null) {
            return diaryEntryRepository.findByUserIdAndEntryDateBetween(userId, startDate, endDate, pageable)
                    .map(this::toDto);
        }

        // No filters provided, return all entries for user
        return diaryEntryRepository.findByUserIdOrderByEntryDateDesc(userId, pageable)
                .map(this::toDto);
    }

    /**
     * Convert DiaryEntry entity to DTO.
     */
    private DiaryEntryDto toDto(DiaryEntry entry) {
        return new DiaryEntryDto(
                entry.getId(),
                entry.getTitle(),
                entry.getContent(),
                entry.getEntryDate(),
                entry.getUser().getId(),
                entry.getCreatedAt(),
                entry.getUpdatedAt()
        );
    }
}
