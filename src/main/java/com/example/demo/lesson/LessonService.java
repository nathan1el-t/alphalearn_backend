package com.example.demo.lesson;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptRepository;
import com.example.demo.contributor.Contributor;
import com.example.demo.contributor.ContributorRepository;
import com.example.demo.lesson.dto.CreateLessonRequest;
import com.example.demo.lesson.dto.LessonDTO;
import com.example.demo.lesson.dto.UpdateLessonRequest;
import com.example.demo.lesson.enums.LessonModerationStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ContributorRepository contributorRepository;
    private final ConceptRepository conceptRepository;
    private final ObjectMapper objectMapper;
    public LessonService(
            LessonRepository lessonRepository,
            ContributorRepository contributorRepository,
            ConceptRepository conceptRepository,
            ObjectMapper objectMapper
    ) {
        this.lessonRepository = lessonRepository;
        this.contributorRepository = contributorRepository;
        this.conceptRepository = conceptRepository;
        this.objectMapper = objectMapper;
    }

    public List<LessonDTO> getLessons(Integer conceptId, UUID contributorId) {
        List<Lesson> lessons;

        if (conceptId != null && contributorId != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Provide either conceptId or contributorId, not both"
            );
        }

        if (conceptId == null && contributorId == null) {
            lessons = lessonRepository.findAll();
        } else if (conceptId != null) {
            lessons = lessonRepository.findByConceptId(conceptId);
        } else {
            lessons = lessonRepository.findByContributor_ContributorId(contributorId);
        }

        return lessons.stream()
                .map(this::toDTO)
                .toList();
    }

    public LessonDTO getLessonById(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));
        return toDTO(lesson);
    }

    @Transactional
    public LessonDTO createLesson(CreateLessonRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
        }

        String title = trimToNull(request.title());
        String learningObjectives = trimToNull(request.learningObjectives());
        Object content = request.content();
        Integer conceptId = request.conceptId();
        UUID contributorId = request.contributorId();

        if (title == null || learningObjectives == null || content == null || conceptId == null || contributorId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "title, learningObjectives, content, conceptId, and contributorId are required"
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
                learningObjectives,
                objectMapper.valueToTree(content),
                submit ? LessonModerationStatus.PENDING : LessonModerationStatus.UNPUBLISHED,
                contributor,
                OffsetDateTime.now()
        );

        Lesson saved = lessonRepository.save(lesson);

        lessonRepository.insertLessonConcept(saved.getLessonId(), concept.getConceptId(), (short) 1);

        return toDTO(saved);
    }

    public LessonDTO updateLesson(Integer lessonId, UpdateLessonRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
        }

        String title = trimToNull(request.title());
        String learningObjectives = trimToNull(request.learningObjectives());
        Object content = request.content();

        if (title == null || learningObjectives == null || content == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "title, learningObjectives, and content are required"
            );
        }

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));

        lesson.setTitle(title);
        lesson.setLearningObjectives(learningObjectives);
        lesson.setContent(objectMapper.valueToTree(content));

        Lesson saved = lessonRepository.save(lesson);
        return toDTO(saved);
    }

    public LessonDTO submitLesson(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));

        lesson.setModerationStatus(LessonModerationStatus.PENDING);
        Lesson saved = lessonRepository.save(lesson);
        return toDTO(saved);
    }

    public LessonDTO unpublishLesson(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));

        lesson.setModerationStatus(LessonModerationStatus.UNPUBLISHED);
        Lesson saved = lessonRepository.save(lesson);
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
                lesson.getLearningObjectives(),
                objectMapper.convertValue(lesson.getContent(), Object.class),
                lesson.getModerationStatus().name(),
                lesson.getContributor().getContributorId(),
                lesson.getCreatedAt()
        );
    }

}
