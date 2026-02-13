package com.example.demo.quizquestion;

import com.example.demo.quizquestion.enums.QuizQuestionType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuizQuestionResponseDTO {
    private Integer questionId;
    private Integer quizId;
    private String questionText;
    private QuizQuestionType questionType;
    private String correct_answer;
}
