package com.example.demo.lesson;

import java.time.OffsetDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.example.demo.contributor.Contributor;
import com.example.demo.lesson.enums.LessonModerationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties({"profile"}) //temporary fix to prevent loop, to review in the future
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}