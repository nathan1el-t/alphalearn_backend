package com.example.demo.lessonconcept;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class LessonConceptId implements Serializable {

    private Integer lessonId;
    private Integer conceptId;

    public LessonConceptId() {}

    public LessonConceptId(Integer lessonId, Integer conceptId) {
        this.lessonId = lessonId;
        this.conceptId = conceptId;
    }

    // equals & hashCode are REQUIRED

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonConceptId)) return false;
        LessonConceptId that = (LessonConceptId) o;
        return Objects.equals(lessonId, that.lessonId)
            && Objects.equals(conceptId, that.conceptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId, conceptId);
    }
}

