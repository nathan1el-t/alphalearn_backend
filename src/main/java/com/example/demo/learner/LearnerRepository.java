package com.example.demo.learner;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  LearnerRepository extends JpaRepository<Learner, UUID>{
    
}
