package com.example.demo.concept;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ConceptService {

    private final ConceptRepository repository;

    public ConceptService(ConceptRepository repository) {
        this.repository = repository;
    }

    // Get all cocncepts
    public List<ConceptDTO> getAllConcepts() {
        return repository.findAll()
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
        Concept concept = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concept not found with id: " + id));

        return new ConceptDTO(
                concept.getConceptId(),
                concept.getTitle(),
                concept.getDescription(),
                concept.getModerationStatus().name(),
                concept.getCreatedAt()
        );
    }

    // Create new concept
    public ConceptDTO createConcept(Concept concept) {

        // Force backend-controlled fields
        concept.setModerationStatus(ModerationStatus.PENDING);
        concept.setCreatedAt(OffsetDateTime.now());

        System.out.println(concept.getModerationStatus().getClass());

        Concept savedConcept = repository.save(concept);

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

        Concept existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concept not found"));

        // Update allowed fields only
        existing.setTitle(updatedConcept.getTitle());
        existing.setDescription(updatedConcept.getDescription());
        existing.setModerationStatus(updatedConcept.getModerationStatus());

        Concept saved = repository.save(existing);

        return new ConceptDTO(
                saved.getConceptId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getModerationStatus().name(),
                saved.getCreatedAt()
        );
    }
}
