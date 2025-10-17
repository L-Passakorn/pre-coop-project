package com.diaries.service;

import com.diaries.dto.CreateDiaryEntryRequest;
import com.diaries.dto.DiaryEntryDto;
import com.diaries.dto.UpdateDiaryEntryRequest;
import com.diaries.entity.DiaryEntry;
import com.diaries.entity.User;
import com.diaries.exception.ForbiddenException;
import com.diaries.exception.ResourceNotFoundException;
import com.diaries.repository.DiaryEntryRepository;
import com.diaries.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing diary entries.
 */
@Service
public class DiaryEntryService {

    private final DiaryEntryRepository diaryEntryRepository;
    private final UserRepository userRepository;

    public DiaryEntryService(DiaryEntryRepository diaryEntryRepository, UserRepository userRepository) {
        this.diaryEntryRepository = diaryEntryRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create a new diary entry for the authenticated user.
     *
     * @param request the diary entry creation request
     * @param userId  the authenticated user's ID
     * @return the created diary entry DTO
     * @throws IllegalArgumentException if user not found
     */
    @Transactional
    public DiaryEntryDto createEntry(CreateDiaryEntryRequest request, Long userId) {
        // Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Create diary entry
        DiaryEntry entry = new DiaryEntry();
        entry.setTitle(request.getTitle());
        entry.setContent(request.getContent());
        entry.setEntryDate(request.getEntryDate());
        entry.setUser(user);

        // Save entry
        entry = diaryEntryRepository.save(entry);

        // Return DTO
        return toDto(entry);
    }

    /**
     * Create a new diary entry for the authenticated user by email.
     *
     * @param request the diary entry creation request
     * @param email   the authenticated user's email
     * @return the created diary entry DTO
     * @throws IllegalArgumentException if user not found
     */
    @Transactional
    public DiaryEntryDto createEntryByEmail(CreateDiaryEntryRequest request, String email) {
        // Get user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return createEntry(request, user.getId());
    }

    /**
     * Get paginated diary entries for the authenticated user.
     *
     * @param userId   the authenticated user's ID
     * @param pageable pagination information
     * @return page of diary entry DTOs
     */
    @Transactional(readOnly = true)
    public Page<DiaryEntryDto> getEntriesByUser(Long userId, Pageable pageable) {
        return diaryEntryRepository.findByUserIdOrderByEntryDateDesc(userId, pageable)
                .map(this::toDto);
    }

    /**
     * Get paginated diary entries for the authenticated user by email.
     *
     * @param email    the authenticated user's email
     * @param pageable pagination information
     * @return page of diary entry DTOs
     */
    @Transactional(readOnly = true)
    public Page<DiaryEntryDto> getEntriesByUserEmail(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return getEntriesByUser(user.getId(), pageable);
    }

    /**
     * Get a single diary entry by ID.
     * Verifies that the entry belongs to the authenticated user.
     *
     * @param entryId the diary entry ID
     * @param userId  the authenticated user's ID
     * @return the diary entry DTO
     * @throws IllegalArgumentException if entry not found
     * @throws AccessDeniedException    if entry doesn't belong to user
     */
    @Transactional(readOnly = true)
    public DiaryEntryDto getEntryById(Long entryId, Long userId) {
        DiaryEntry entry = diaryEntryRepository.findById(entryId)
                .orElseThrow(() -> new ResourceNotFoundException("Diary entry not found"));

        // Verify ownership
        if (!entry.getUser().getId().equals(userId)) {
            throw new ForbiddenException("You don't have permission to access this diary entry");
        }

        return toDto(entry);
    }

    /**
     * Get a single diary entry by ID and user email.
     * Verifies that the entry belongs to the authenticated user.
     *
     * @param entryId the diary entry ID
     * @param email   the authenticated user's email
     * @return the diary entry DTO
     * @throws IllegalArgumentException if entry not found
     * @throws AccessDeniedException    if entry doesn't belong to user
     */
    @Transactional(readOnly = true)
    public DiaryEntryDto getEntryByIdAndEmail(Long entryId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return getEntryById(entryId, user.getId());
    }

    /**
     * Update an existing diary entry.
     * Verifies that the entry belongs to the authenticated user.
     *
     * @param entryId the diary entry ID
     * @param request the update request
     * @param userId  the authenticated user's ID
     * @return the updated diary entry DTO
     * @throws IllegalArgumentException if entry not found
     * @throws AccessDeniedException    if entry doesn't belong to user
     */
    @Transactional
    public DiaryEntryDto updateEntry(Long entryId, UpdateDiaryEntryRequest request, Long userId) {
        DiaryEntry entry = diaryEntryRepository.findById(entryId)
                .orElseThrow(() -> new ResourceNotFoundException("Diary entry not found"));

        // Verify ownership
        if (!entry.getUser().getId().equals(userId)) {
            throw new ForbiddenException("You don't have permission to update this diary entry");
        }

        // Update fields if provided
        if (request.getTitle() != null) {
            entry.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            entry.setContent(request.getContent());
        }
        if (request.getEntryDate() != null) {
            entry.setEntryDate(request.getEntryDate());
        }

        // Save and return
        entry = diaryEntryRepository.save(entry);
        return toDto(entry);
    }

    /**
     * Update an existing diary entry by email.
     * Verifies that the entry belongs to the authenticated user.
     *
     * @param entryId the diary entry ID
     * @param request the update request
     * @param email   the authenticated user's email
     * @return the updated diary entry DTO
     * @throws IllegalArgumentException if entry not found
     * @throws AccessDeniedException    if entry doesn't belong to user
     */
    @Transactional
    public DiaryEntryDto updateEntryByEmail(Long entryId, UpdateDiaryEntryRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return updateEntry(entryId, request, user.getId());
    }

    /**
     * Delete a diary entry.
     * Verifies that the entry belongs to the authenticated user.
     *
     * @param entryId the diary entry ID
     * @param userId  the authenticated user's ID
     * @throws IllegalArgumentException if entry not found
     * @throws AccessDeniedException    if entry doesn't belong to user
     */
    @Transactional
    public void deleteEntry(Long entryId, Long userId) {
        DiaryEntry entry = diaryEntryRepository.findById(entryId)
                .orElseThrow(() -> new ResourceNotFoundException("Diary entry not found"));

        // Verify ownership
        if (!entry.getUser().getId().equals(userId)) {
            throw new ForbiddenException("You don't have permission to delete this diary entry");
        }

        diaryEntryRepository.delete(entry);
    }

    /**
     * Delete a diary entry by email.
     * Verifies that the entry belongs to the authenticated user.
     *
     * @param entryId the diary entry ID
     * @param email   the authenticated user's email
     * @throws IllegalArgumentException if entry not found
     * @throws AccessDeniedException    if entry doesn't belong to user
     */
    @Transactional
    public void deleteEntryByEmail(Long entryId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        deleteEntry(entryId, user.getId());
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
