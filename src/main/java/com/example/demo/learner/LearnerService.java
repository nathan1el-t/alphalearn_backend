package com.example.demo.learner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.learner.dto.LearnerResponseDTO;
import com.example.demo.lessonenrollment.LessonEnrollment;
import com.example.demo.quizattempt.QuizAttempt;

@Service
public class LearnerService {
    
    private final LearnerRepository learnerRepository;

    public LearnerService(LearnerRepository learnerRepository) {
        this.learnerRepository = learnerRepository;
    }

    public List<LearnerResponseDTO> getAllLearners() {
        return learnerRepository.findAll()
            .stream()
            .map(learner -> new LearnerResponseDTO(
                learner.getId(),
                learner.getUsername(),
                learner.getCreatedAt(),
                learner.getTotalPoints(),
                learner.getQuizAttempt()
                    .stream()
                    .map(QuizAttempt::getAttemptId)
                    .toList(),
                learner.getLessonEnrollment()
                    .stream()
                    .map(LessonEnrollment::getEnrollmentId)
                    .toList()
            )).toList();
    }

    public LearnerResponseDTO getLearnerById(UUID id){
        Learner learner = learnerRepository.findById(id).orElseThrow(() -> new RuntimeException("Learner not found"));
        return new LearnerResponseDTO(
            learner.getId(),
            learner.getUsername(),
            learner.getCreatedAt(),
            learner.getTotalPoints(),
            learner.getQuizAttempt()
                .stream()
                .map(QuizAttempt::getAttemptId)
                .toList(),
            learner.getLessonEnrollment()
                .stream()
                .map(LessonEnrollment::getEnrollmentId)
                .toList()
        );
    }

    public Learner getLearnerEntityById(UUID id){
        Learner learner = learnerRepository.findById(id).orElseThrow(() -> new RuntimeException("Learner not found"));
        return learner;
    }
}
