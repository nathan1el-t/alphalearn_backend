package com.example.demo.learners;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LearnerService {

    private final LearnerRepository learnerRepository;

    public LearnerService(LearnerRepository learnerRepository) {
        this.learnerRepository = learnerRepository;
    }

    public List<Learner> getAllLearners() {
        return learnerRepository.findAll();
    }
}