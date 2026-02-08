package com.example.demo.lessonconcept;

import com.example.demo.concept.Concept;
import com.example.demo.lesson.Lesson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties({"profile"})
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "lesson_concepts")
public class LessonConcept {

    @EmbeddedId
    @Setter(lombok.AccessLevel.NONE)
    private LessonConceptId id;

    @ManyToOne
    @MapsId("lessonId")
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @MapsId("conceptId")
    @JoinColumn(name = "concept_id")
    private Concept concept;

    @Column(name = "display_order", nullable = false)
    private short displayOrder;
}