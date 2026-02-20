package com.example.demo.concept;

import java.time.OffsetDateTime;
import java.util.List;

import com.example.demo.lessonconcept.LessonConceptRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ConceptService {

    private final ConceptRepository conceptRepository;
    private final LessonConceptRepository lessonConceptRepository;

    public ConceptService(
            ConceptRepository conceptRepository,
            LessonConceptRepository lessonConceptRepository
    ) {
        this.conceptRepository = conceptRepository;
        this.lessonConceptRepository = lessonConceptRepository;
    }

    // Get all cocncepts
    public List<ConceptDTO> getAllConcepts() {
        return conceptRepository.findAll()
                .stream()
                .map(concept -> new ConceptDTO(
                        concept.getConceptId(),
                        concept.getTitle(),
                        concept.getDescription(),
                        concept.getModerationStatus().name(),
                        concept.getCreatedAt()
                ))
                .toList();
    }

    // Get concept by ID
    public ConceptDTO getConceptById(Integer id) {
        Concept concept = conceptRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Concept not found: " + id));

        return new ConceptDTO(
                concept.getConceptId(),
                concept.getTitle(),
                concept.getDescription(),
                concept.getModerationStatus().name(),
                concept.getCreatedAt()
        );
    }

    // Create new concept
    public ConceptDTO createConcept(ConceptCreateDTO request) {

    Concept concept = new Concept();

    concept.setTitle(request.title());
    concept.setDescription(request.description());

    // Backend controlled fields
    concept.setModerationStatus(ModerationStatus.PENDING);
    concept.setCreatedAt(OffsetDateTime.now());

    Concept savedConcept = conceptRepository.save(concept);

    return new ConceptDTO(
            savedConcept.getConceptId(),
            savedConcept.getTitle(),
            savedConcept.getDescription(),
            savedConcept.getModerationStatus().name(),
            savedConcept.getCreatedAt()
    );
}

    // Update existing concept
    public ConceptDTO updateConcept(Integer id, Concept updatedConcept) {

        Concept existing = conceptRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Concept not found: " + id));

        // Update allowed fields only
        existing.setTitle(updatedConcept.getTitle());
        existing.setDescription(updatedConcept.getDescription());
        existing.setModerationStatus(updatedConcept.getModerationStatus());

        Concept saved = conceptRepository.save(existing);

        return new ConceptDTO(
                saved.getConceptId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getModerationStatus().name(),
                saved.getCreatedAt()
        );
    }

    // Delete concept by ID
    public void deleteConcept(Integer id) {

        Concept concept = conceptRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Concept not found: " + id));

        long linkedLessons = lessonConceptRepository.countByIdConceptId(id);
        if (linkedLessons > 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete concept " + id + " because it is used by " + linkedLessons
                            + " lesson(s). Remove the concept from those lessons first."
            );
        }

        conceptRepository.delete(concept);
    }
}
