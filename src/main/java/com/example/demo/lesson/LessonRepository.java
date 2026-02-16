package com.example.demo.lesson;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByContributor_ContributorId(UUID contributorId);
    boolean existsByContributor_ContributorId(UUID contributorId);

    @Query(
            value = """
                select distinct l.*
                from lessons l
                join lesson_concepts lc on lc.lesson_id = l.lesson_id
                where lc.concept_id in (:conceptIds)
            """,
            nativeQuery = true
    )
    List<Lesson> findByConceptIds(@Param("conceptIds") List<Integer> conceptIds);

    @Query(
            value = """
                select l.*
                from lessons l 
                join lesson_concepts lc on lc.lesson_id = l.lesson_id
                where lc.concept_id in (:conceptIds)
                group by l.lesson_id
                having count(distinct lc.concept_id) = :conceptCount
            """,
            nativeQuery = true
    )
    List<Lesson> findByAllConceptIds(
            @Param("conceptIds") List<Integer> conceptIds,
            @Param("conceptCount")  Integer conceptCount);

    @Query(
            value = """
                select distinct l.*
                from lessons l
                join lesson_concepts lc on lc.lesson_id = l.lesson_id
                where l.contributor_id = :contributorId
                  and lc.concept_id in (:conceptIds)
            """,
            nativeQuery = true
    )
    List<Lesson> findByContributorAndConceptIds(
            @Param("contributorId") UUID contributorId,
            @Param("conceptIds") List<Integer> conceptIds
    );

    @Query(
            value = """
                select l.*
                from lessons l
                join lesson_concepts lc on lc.lesson_id = l.lesson_id
                where l.contributor_id = :contributorId
                  and lc.concept_id in (:conceptIds)
                group by l.lesson_id
                having count(distinct lc.concept_id) = :conceptCount
            """,
            nativeQuery = true
    )
    List<Lesson> findByContributorAndAllConceptIds(
            @Param("contributorId") UUID contributorId,
            @Param("conceptIds") List<Integer> conceptIds,
            @Param("conceptCount") Integer conceptCount
    );

    // Temporary: raw insert while lesson_concepts has display_order.
    // Replace with @ManyToMany after display_order is removed in DB.
    @Modifying
    @Query(
            value = "insert into lesson_concepts (lesson_id, concept_id, display_order) values (:lessonId, :conceptId, :displayOrder)", nativeQuery = true)
    void insertLessonConcept(Integer lessonId, Integer conceptId, short displayOrder);
}
