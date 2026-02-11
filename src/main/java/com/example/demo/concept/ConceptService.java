package com.example.demo.concept;

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
                        concept.getModerationStatus(),
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
                concept.getModerationStatus(),
                concept.getCreatedAt()
        );
    }
}
