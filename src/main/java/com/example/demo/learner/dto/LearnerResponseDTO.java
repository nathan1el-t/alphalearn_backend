package com.example.demo.learner.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record LearnerResponseDTO(
    UUID id,
    String username,
    OffsetDateTime createdAt,
    Short totalPoints,
    List<Integer> quizAttempts,
    List<Integer> lessonEnrollments
) {}
