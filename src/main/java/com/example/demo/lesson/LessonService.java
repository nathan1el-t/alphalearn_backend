package com.example.demo.lesson;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptRepository;
import com.example.demo.contributor.Contributor;
import com.example.demo.contributor.ContributorRepository;
import com.example.demo.lesson.dto.CreateLessonRequest;
import com.example.demo.lesson.dto.LessonDTO;
import com.example.demo.lesson.enums.LessonModerationStatus;
import com.example.demo.lessonconcept.LessonConcept;
import com.example.demo.lessonconcept.LessonConceptId;
import com.example.demo.lessonconcept.LessonConceptRepository;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ContributorRepository contributorRepository;
    private final ConceptRepository conceptRepository;
    private final LessonConceptRepository lessonConceptRepository;

    public LessonService(
            LessonRepository lessonRepository,
            ContributorRepository contributorRepository,
            ConceptRepository conceptRepository,
            LessonConceptRepository lessonConceptRepository
    ) {
        this.lessonRepository = lessonRepository;
        this.contributorRepository = contributorRepository;
        this.conceptRepository = conceptRepository;
        this.lessonConceptRepository = lessonConceptRepository;
    }

    public List<LessonDTO> getAllLessons() {
        return lessonRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public LessonDTO getLessonById(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));
        return toDTO(lesson);
    }

    public LessonDTO createLesson(CreateLessonRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
        }

        String title = trimToNull(request.title());
        JsonNode content = request.content();
        Integer conceptId = request.conceptId();
        UUID contributorId = request.contributorId();

        if (title == null || content == null || conceptId == null || contributorId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "title, content, conceptId, and contributorId are required"
            );
        }

        Contributor contributor = contributorRepository.findById(contributorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contributor not found"));
        Concept concept = conceptRepository.findById(conceptId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Concept not found"));

        boolean submit = Boolean.TRUE.equals(request.submit());
        Lesson lesson = new Lesson(
                null,
                title,
                content,
                submit ? LessonModerationStatus.PENDING : LessonModerationStatus.UNPUBLISHED,
                contributor,
                OffsetDateTime.now()
        );

        Lesson saved = lessonRepository.save(lesson);

        LessonConcept lessonConcept = new LessonConcept(
                new LessonConceptId(saved.getLessonId(), concept.getConceptId()),
                saved,
                concept,
                (short) 1
        );
        lessonConceptRepository.save(lessonConcept);

        return toDTO(saved);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private LessonDTO toDTO(Lesson lesson) {
        return new LessonDTO(
                lesson.getLessonId(),
                lesson.getTitle(),
                lesson.getContent(),
                lesson.getModerationStatus().name(),
                lesson.getContributor().getContributorId(),
                lesson.getCreatedAt()
        );
    }

}
