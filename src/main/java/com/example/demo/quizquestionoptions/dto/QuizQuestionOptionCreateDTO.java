package com.example.demo.quizquestionoptions.dto;

public record QuizQuestionOptionCreateDTO(
    Integer questionId,
    String optionKey,
    String optionText
) {}
