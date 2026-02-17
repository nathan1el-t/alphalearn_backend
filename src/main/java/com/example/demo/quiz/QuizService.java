package com.example.demo.quiz;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if(id == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz id cannot be null");}
        return quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));
    }
    
    public QuizResponseDTO getQuizByIdDTO(Integer id) {
        if(id == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz id cannot be null");}
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));
        return new QuizResponseDTO(quiz.getQuizId(), quiz.getLesson().getLessonId());
    }
    
    public QuizResponseDTO createQuiz(QuizCreateDTO request){
        if(request == null || request.lessonId() == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request cannot be null");}
        Quiz quiz = new Quiz(lessonService.getLessonEntityById(request.lessonId()));
        Quiz createdQuiz = quizRepository.saveAndFlush(quiz);
        return new QuizResponseDTO(
            createdQuiz.getQuizId(),
            createdQuiz.getLesson().getLessonId()
        );
    }
    
    public QuizResponseDTO updateQuiz(Integer id, QuizCreateDTO request){
        if(id == null || request == null || request.lessonId() == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz id or request cannot be null");}
        Quiz existingQuiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));
        existingQuiz.setLesson(lessonService.getLessonEntityById(request.lessonId()));
        Quiz updatedQuiz = quizRepository.save(existingQuiz); //save does both create and update, if the entity's id does not exist then it creates, if it does then it updates
        return new QuizResponseDTO(
            updatedQuiz.getQuizId(),
            updatedQuiz.getLesson().getLessonId()
        );
    }
    
    public void deleteQuiz(Integer id){
        if(id == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz id or request cannot be null");}
        Quiz existingQuiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));
        quizRepository.delete(existingQuiz);
    }
}
