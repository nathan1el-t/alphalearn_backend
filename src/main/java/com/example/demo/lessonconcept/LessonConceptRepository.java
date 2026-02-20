package com.example.demo.lessonconcept;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonConceptRepository extends JpaRepository<LessonConcept, LessonConceptId> {
    long countByIdConceptId(Integer conceptId);
}
