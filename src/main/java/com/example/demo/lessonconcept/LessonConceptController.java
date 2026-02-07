package com.example.demo.lessonconcept;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lessonconcepts")
public class LessonConceptController {
    
    private final LessonConceptService service;

    public LessonConceptController(LessonConceptService service) {
        this.service = service;
    }

    @GetMapping
    public List<LessonConcept> getAll() {
        return service.getAll();
    }
}
