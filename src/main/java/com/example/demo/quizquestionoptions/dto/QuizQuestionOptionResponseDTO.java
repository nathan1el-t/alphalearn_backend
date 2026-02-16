package com.example.demo.quizquestionoptions.dto;

import java.time.OffsetDateTime;

public record QuizQuestionOptionResponseDTO(
    Integer optionId,
    Integer questionId,
    String optionKey,
    String optionText,
    OffsetDateTime createdAt
) {}
