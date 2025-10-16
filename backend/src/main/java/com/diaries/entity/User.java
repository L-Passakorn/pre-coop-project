package com.diaries.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User entity representing a registered user in the system.
 * Each user can have multiple diary entries.
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_user_email", columnList = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password; // BCrypt hashed password

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DiaryEntry> diaryEntries = new ArrayList<>();

    /**
     * Default constructor required by JPA.
     */
    public User() {
    }

    /**
     * Constructor for creating a new user.
     *
     * @param email    the user's email address
     * @param password the user's hashed password
     * @param fullName the user's full name
     */
    public User(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public List<DiaryEntry> getDiaryEntries() {
        return diaryEntries;
    }

    public void setDiaryEntries(List<DiaryEntry> diaryEntries) {
        this.diaryEntries = diaryEntries;
    }

    /**
     * Helper method to add a diary entry to this user.
     * Maintains bidirectional relationship.
     *
     * @param diaryEntry the diary entry to add
     */
    public void addDiaryEntry(DiaryEntry diaryEntry) {
        diaryEntries.add(diaryEntry);
        diaryEntry.setUser(this);
    }

    /**
     * Helper method to remove a diary entry from this user.
     * Maintains bidirectional relationship.
     *
     * @param diaryEntry the diary entry to remove
     */
    public void removeDiaryEntry(DiaryEntry diaryEntry) {
        diaryEntries.remove(diaryEntry);
        diaryEntry.setUser(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
