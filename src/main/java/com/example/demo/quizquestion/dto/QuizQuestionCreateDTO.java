package com.example.demo.quizquestion.dto;

import com.example.demo.quizquestion.enums.QuizQuestionType;

public record QuizQuestionCreateDTO(
    Integer quizId,
    String questionText,
    QuizQuestionType quizQuestionType,
    String correctAnswer
) {}
