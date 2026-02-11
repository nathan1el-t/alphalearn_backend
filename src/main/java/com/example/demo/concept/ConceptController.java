package com.example.demo.concept;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    
}

