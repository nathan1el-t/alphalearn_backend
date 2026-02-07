package com.example.demo.lessonconcept;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LessonConceptService {
    private final LessonConceptRepository repo;

    public LessonConceptService(LessonConceptRepository repo) {
        this.repo = repo;
    }

    public List<LessonConcept> getAll() {
        return repo.findAll();
    }
}
