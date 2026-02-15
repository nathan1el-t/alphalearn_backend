package com.example.demo.lesson;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByContributor_ContributorId(UUID contributorId);

    // Temporary: native join while lesson_concepts is managed without an entity.
    // Replace with @ManyToMany + derived query after display_order is removed in DB.
    @Query(value = "select l.* from lessons l join lesson_concepts lc on l.lesson_id = lc.lesson_id where lc.concept_id = :conceptId", nativeQuery = true)
    List<Lesson> findByConceptId(Integer conceptId);

    // Temporary: raw insert while lesson_concepts has display_order.
    // Replace with @ManyToMany after display_order is removed in DB.
    @Modifying
    @Query(value = "insert into lesson_concepts (lesson_id, concept_id, display_order) values (:lessonId, :conceptId, :displayOrder)", nativeQuery = true)
    void insertLessonConcept(Integer lessonId, Integer conceptId, short displayOrder);
}
