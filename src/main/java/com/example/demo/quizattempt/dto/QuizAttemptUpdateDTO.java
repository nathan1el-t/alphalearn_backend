package com.example.demo.quizattempt.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record QuizAttemptUpdateDTO(
    Integer quizId,
    Short score,
    Boolean isFirstAttempt,
    OffsetDateTime attemptedAt,
    UUID learnerId
) {}
