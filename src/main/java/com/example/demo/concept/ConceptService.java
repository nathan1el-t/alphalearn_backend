package com.example.demo.concept;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ConceptService {

    private final ConceptRepository repository;

    public ConceptService(ConceptRepository repository) {
        this.repository = repository;
    }

    public List<Concept> getAllConcepts() {
        return repository.findAll();
    }
}
