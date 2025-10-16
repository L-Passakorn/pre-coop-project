package com.diaries.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DiaryEntry entity representing a diary entry created by a user.
 * Each entry belongs to exactly one user.
 */
@Entity
@Table(name = "diary_entries", indexes = {
    @Index(name = "idx_user_date", columnList = "user_id,entry_date"),
    @Index(name = "idx_entry_date", columnList = "entry_date"),
    @Index(name = "idx_user_id", columnList = "user_id")
})
public class DiaryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_diary_entry_user"))
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Default constructor required by JPA.
     */
    public DiaryEntry() {
    }

    /**
     * Constructor for creating a new diary entry.
     *
     * @param title      the entry title
     * @param content    the entry content
     * @param entryDate  the date of the entry
     * @param user       the user who owns this entry
     */
    public DiaryEntry(String title, String content, LocalDate entryDate, User user) {
        this.title = title;
        this.content = content;
        this.entryDate = entryDate;
        this.user = user;
    }

    // Getters and Setters

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaryEntry that = (DiaryEntry) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DiaryEntry{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", entryDate=" + entryDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
