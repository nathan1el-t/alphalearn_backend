package com.example.demo.lesson;

import java.time.OffsetDateTime;

import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.example.demo.contributor.Contributor;
import com.example.demo.lesson.enums.LessonModerationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@JsonIgnoreProperties({"profile"}) //temporary fix to prevent loop, to review in the future
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    @Setter(lombok.AccessLevel.NONE)
    private Integer lessonId;

    @Column(nullable = false)
    private String title;

    // to be removed
    @Column(name = "learning_objectives")
    private String learningObjectives;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "content", columnDefinition = "jsonb", nullable = false)
    private JsonNode content;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", nullable = false, columnDefinition = "lessons_moderation_status")
    private LessonModerationStatus lessonModerationStatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "contributor_id", nullable = false)
    private Contributor contributor;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    public Lesson(
            String title,
            String learningObjectives,
            JsonNode content,
            LessonModerationStatus lessonModerationStatus,
            Contributor contributor,
            OffsetDateTime createdAt
    ) {
        this.title = title;
        this.learningObjectives = learningObjectives;
        this.content = content;
        this.lessonModerationStatus = lessonModerationStatus;
        this.contributor = contributor;
        this.createdAt = createdAt;
    }
}
