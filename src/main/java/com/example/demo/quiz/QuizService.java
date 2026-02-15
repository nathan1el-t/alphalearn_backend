package com.example.demo.quiz;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.lesson.LessonService;

@Service
public class QuizService {

    private final LessonService lessonService;
    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository, LessonService lessonService) {
        this.quizRepository = quizRepository;
        this.lessonService = lessonService;
    }

    public List<QuizResponseDTO> getAllQuizzes() {
        return quizRepository.findAll().stream().map(quiz -> new QuizResponseDTO(quiz.getQuizId(),quiz.getLessonId().getLessonId())).toList();
    }

    public QuizResponseDTO getQuizById(Integer id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
        return new QuizResponseDTO(quiz.getQuizId(), quiz.getLessonId().getLessonId());
    }
}
