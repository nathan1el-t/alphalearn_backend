package com.example.demo.quizquestion.dto;

import com.example.demo.quizquestion.enums.QuizQuestionType;

public record QuizQuestionResponseDTO(
    Integer questionId,
    Integer quizId,
    String questionText,
    QuizQuestionType quizQuestionType,
    String correctAnswer
) {}
