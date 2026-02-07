package com.example.demo.lesson;

import java.time.OffsetDateTime;

import com.example.demo.contributor.Contributor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@JsonIgnoreProperties({"profile"}) //temporary fix to prevent loop, to review in the future
@Entity
@Table(name = "lessons")
public class Lesson {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Integer lessonId;

    @Column(nullable = false)
    private String title;

    @Column(name = "learning_objectives", nullable = false)
    private String learningObjectives;

    @Column(name = "moderation_status", nullable = false)
    private String moderationStatus;    // Enum later, keep String for now to avoid enum mismatch
                                        // Enums are: PENDING, APPROVED, UNPUBLISHED

    @ManyToOne(optional = false)
    @JoinColumn(name = "contributor_id", nullable = false)
    private Contributor contributor;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    // Getters

    public Integer getLessonId() {
        return lessonId;
    }

    public String getTitle() {
        return title;
    }

    public String getLearningObjectives() {
        return learningObjectives;
    }

    public String getModerationStatus() {
        return moderationStatus;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLearningObjectives(String learningObjectives) {
        this.learningObjectives = learningObjectives;
    }

    public void setModerationStatus(String moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
