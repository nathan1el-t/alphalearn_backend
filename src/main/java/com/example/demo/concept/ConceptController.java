package com.example.demo.concept;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/concepts")
public class ConceptController {

    private final ConceptService service;

    public ConceptController(ConceptService service) {
        this.service = service;
    }

    /**
     * Get all concepts
     */
    @GetMapping
    public List<ConceptDTO> getConcepts() {
        return service.getAllConcepts();
    }

    /**
     * Get concept by ID
     */
    @GetMapping("/{id}")
    public ConceptDTO getConceptById(@PathVariable Integer id) {
        return service.getConceptById(id);
    }

    /**
     * Post new concept
     * The json body should NOT contain fields: moderationStatus, createdAt, and conceptId.
     */
    @PostMapping
    public ConceptDTO createConcept(@RequestBody ConceptCreateDTO concept) {
        return service.createConcept(concept);
    }
    
    /**
     * Update existing concept
     */
    @PutMapping("/{id}")
    public ConceptDTO updateConcept(
            @PathVariable Integer id,
            @RequestBody Concept updatedConcept) {

        return service.updateConcept(id, updatedConcept);
    }

    /**
     * Delete concept by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcept(@PathVariable Integer id) {
        service.deleteConcept(id);
        return ResponseEntity.noContent().build();
    }

}

