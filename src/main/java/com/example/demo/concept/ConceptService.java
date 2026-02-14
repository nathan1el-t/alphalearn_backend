package com.example.demo.concept;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ConceptService {

    private final ConceptRepository conceptRepository;

    public ConceptService(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
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
                .orElseThrow(() -> new RuntimeException("Concept not found"));

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
        .orElseThrow(() -> new RuntimeException("Concept not found with id: " + id));

        conceptRepository.delete(concept);
    }
}
