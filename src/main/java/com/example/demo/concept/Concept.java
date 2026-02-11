package com.example.demo.concept;

import java.time.OffsetDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "concepts")
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concept_id")
    @Setter(lombok.AccessLevel.NONE)
    private Integer conceptId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    // @Column(name = "moderation_status", nullable = false)
    // private String moderationStatus;
    // // mapped as String to avoid enum mismatch
    // // Enums: PENDING, APPROVED, REJECTED

    @Column(name = "moderation_status", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private ModerationStatus moderationStatus;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}