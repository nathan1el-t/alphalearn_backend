package com.example.demo.lessonconcept;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LessonConceptId implements Serializable {

    private Integer lessonId;
    private Integer conceptId;
}