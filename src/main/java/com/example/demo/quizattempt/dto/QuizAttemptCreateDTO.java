package com.example.demo.quizattempt.dto;

import java.util.UUID;

public record QuizAttemptCreateDTO(
    Integer quizId,
    Short score,
    Boolean isFirstAttempt,
    UUID learnerId
) {}
