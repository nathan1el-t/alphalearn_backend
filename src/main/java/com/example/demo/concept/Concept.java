package com.example.demo.concept;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "concepts")
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concept_id")
    private Integer conceptId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "moderation_status", nullable = false)
    private String moderationStatus;
    // mapped as String to avoid enum mismatch
    // Enums: PENDING, APPROVED, REJECTED

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    // Getters

    public Integer getConceptId() {
        return conceptId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getModerationStatus() {
        return moderationStatus;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setModerationStatus(String moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
