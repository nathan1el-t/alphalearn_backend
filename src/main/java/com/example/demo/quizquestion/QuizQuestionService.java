package com.example.demo.quizquestion;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class QuizQuestionService {
    private final QuizQuestionRepository quizQuestionRepository;

    public QuizQuestionService(QuizQuestionRepository quizQuestionRepository){
        this.quizQuestionRepository = quizQuestionRepository;
    }

    public List<QuizQuestionResponseDTO> getAllQuizQuestions(){
        return quizQuestionRepository.findAll()
                .stream()
                .map(quizQuestion -> new QuizQuestionResponseDTO(
                    quizQuestion.getQuestionId(),
                    quizQuestion.getQuizId().getQuizId(),
                    quizQuestion.getQuestionText(),
                    quizQuestion.getQuizQuestionType(),
                    quizQuestion.getCorrectAnswer()
                )).toList();
    }
}
