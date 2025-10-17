package com.diaries.controller;

import com.diaries.dto.CreateDiaryEntryRequest;
import com.diaries.dto.DiaryEntryDto;
import com.diaries.dto.UpdateDiaryEntryRequest;
import com.diaries.service.DiaryEntryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for diary entry operations.
 * All endpoints require authentication.
 */
@RestController
@RequestMapping("/api/diary-entries")
public class DiaryEntryController {

    private final DiaryEntryService diaryEntryService;

    public DiaryEntryController(DiaryEntryService diaryEntryService) {
        this.diaryEntryService = diaryEntryService;
    }

    /**
     * Create a new diary entry.
     *
     * @param request        the diary entry creation request
     * @param authentication the authenticated user
     * @return the created diary entry
     */
    @PostMapping
    public ResponseEntity<?> createEntry(
            @Valid @RequestBody CreateDiaryEntryRequest request,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            DiaryEntryDto entry = diaryEntryService.createEntryByEmail(request, email);
            return ResponseEntity.status(HttpStatus.CREATED).body(entry);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Get paginated list of diary entries for the authenticated user.
     *
     * @param page           page number (default 0)
     * @param size           page size (default 10)
     * @param authentication the authenticated user
     * @return page of diary entries
     */
    @GetMapping
    public ResponseEntity<Page<DiaryEntryDto>> getEntries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        String email = authentication.getName();
        Pageable pageable = PageRequest.of(page, size);
        Page<DiaryEntryDto> entries = diaryEntryService.getEntriesByUserEmail(email, pageable);
        return ResponseEntity.ok(entries);
    }

    /**
     * Get a single diary entry by ID.
     *
     * @param id             the diary entry ID
     * @param authentication the authenticated user
     * @return the diary entry
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getEntry(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            DiaryEntryDto entry = diaryEntryService.getEntryByIdAndEmail(id, email);
            return ResponseEntity.ok(entry);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Update an existing diary entry.
     *
     * @param id             the diary entry ID
     * @param request        the update request
     * @param authentication the authenticated user
     * @return the updated diary entry
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntry(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDiaryEntryRequest request,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            DiaryEntryDto entry = diaryEntryService.updateEntryByEmail(id, request, email);
            return ResponseEntity.ok(entry);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Delete a diary entry.
     *
     * @param id             the diary entry ID
     * @param authentication the authenticated user
     * @return no content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntry(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            diaryEntryService.deleteEntryByEmail(id, email);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Simple error response DTO.
     */
    private record ErrorResponse(String message) {
    }
}
