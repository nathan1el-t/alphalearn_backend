package com.example.demo.quizattempt.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record QuizAttemptResponseDTO(
    Integer attemptId,
    Integer quizId,
    Short score,
    Boolean isFirstAttempt,
    OffsetDateTime attemptedAt,
    UUID learnerId
) {}