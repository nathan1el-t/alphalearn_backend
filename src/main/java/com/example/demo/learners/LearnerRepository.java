package com.example.demo.learners;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LearnerRepository extends JpaRepository<Learner, UUID> {
    
}
