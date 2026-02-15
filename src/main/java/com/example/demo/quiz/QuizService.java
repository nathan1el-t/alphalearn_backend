package com.example.demo.quiz;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.lesson.LessonService;
import com.example.demo.quiz.dto.QuizCreateDTO;
import com.example.demo.quiz.dto.QuizResponseDTO;

@Service
public class QuizService {

    private final LessonService lessonService;
    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository, LessonService lessonService) {
        this.quizRepository = quizRepository;
        this.lessonService = lessonService;
    }

    public List<QuizResponseDTO> getAllQuizzes() {
        return quizRepository.findAll().stream().map(quiz -> new QuizResponseDTO(quiz.getQuizId(),quiz.getLesson().getLessonId())).toList();
    }

    public Quiz getQuizById(Integer id){
        return quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    public QuizResponseDTO getQuizByIdDTO(Integer id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
        return new QuizResponseDTO(quiz.getQuizId(), quiz.getLesson().getLessonId());
    }

    public Quiz createQuiz(QuizCreateDTO request){
        Quiz quiz = new Quiz(lessonService.getLessonById(request.lessonId()));
        return quizRepository.saveAndFlush(quiz);
    }
}
