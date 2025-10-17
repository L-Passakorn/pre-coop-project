package com.diaries.dto;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * DTO for updating an existing diary entry.
 * All fields are optional to allow partial updates.
 */
public class UpdateDiaryEntryRequest {

    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 10000, message = "Content must not exceed 10000 characters")
    private String content;

    private LocalDate entryDate;

    public UpdateDiaryEntryRequest() {
    }

    public UpdateDiaryEntryRequest(String title, String content, LocalDate entryDate) {
        this.title = title;
        this.content = content;
        this.entryDate = entryDate;
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
}
