package com.example.demo.learner;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.learner.dto.LearnerResponseDTO;


@RestController
@RequestMapping("/api/learners")
public class LearnerController {
    
    private final LearnerService learnerService;

    public LearnerController(LearnerService learnerService) {
        this.learnerService = learnerService;
    }

    @GetMapping
    public List<LearnerResponseDTO> getLearners() {
        return learnerService.getAllLearners();
    }
    
    @GetMapping("/{id}")
    public LearnerResponseDTO getLearnerById(@PathVariable UUID id){
        return learnerService.getLearnerById(id);
    }
}
