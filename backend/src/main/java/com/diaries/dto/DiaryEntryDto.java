package com.diaries.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for diary entry responses.
 * Contains all diary entry information for API responses.
 */
public class DiaryEntryDto {

    private Long id;
    private String title;
    private String content;
    private LocalDate entryDate;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DiaryEntryDto() {
    }

    public DiaryEntryDto(Long id, String title, String content, LocalDate entryDate, 
                         Long userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.entryDate = entryDate;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
