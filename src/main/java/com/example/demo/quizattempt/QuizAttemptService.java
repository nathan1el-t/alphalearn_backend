package com.example.demo.quizattempt;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.learner.LearnerService;
import com.example.demo.quiz.QuizService;
import com.example.demo.quizattempt.dto.QuizAttemptCreateDTO;
import com.example.demo.quizattempt.dto.QuizAttemptResponseDTO;
import com.example.demo.quizattempt.dto.QuizAttemptUpdateDTO;

@Service
public class QuizAttemptService {
    private final QuizAttemptRepository quizAttemptRepository;
    private final QuizService quizService;
    private final LearnerService learnerService;

    public QuizAttemptService(QuizAttemptRepository quizAttemptRepository, QuizService quizService, LearnerService learnerService){
        this.quizAttemptRepository = quizAttemptRepository;
        this.quizService = quizService;
        this.learnerService = learnerService;
    }

    public List<QuizAttemptResponseDTO> getAllQuizAttempts(){
        return quizAttemptRepository.findAll()
                .stream()
                .map(quizAttempt -> new QuizAttemptResponseDTO(
                    quizAttempt.getAttemptId(),
                    quizAttempt.getQuiz().getQuizId(),
                    quizAttempt.getScore(),
                    quizAttempt.getIsFirstAttempt(),
                    quizAttempt.getAttemptedAt(),
                    quizAttempt.getLearner().getId()
                )).toList();
    }

    public QuizAttemptResponseDTO getQuizAttemptById(Integer id){
        if(id == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz attempt id cannot be null");}
        QuizAttempt quizAttempt = quizAttemptRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz Attempt not found"));
        return new QuizAttemptResponseDTO(
            quizAttempt.getAttemptId(),
            quizAttempt.getQuiz().getQuizId(),
            quizAttempt.getScore(),
            quizAttempt.getIsFirstAttempt(),
            quizAttempt.getAttemptedAt(),
            quizAttempt.getLearner().getId()
        );
    }

    public QuizAttemptResponseDTO createQuizAttempt(QuizAttemptCreateDTO request){
        if(request == null || request.quizId() == null || request.learnerId() == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz attempt request cannot be null");}
        QuizAttempt quizAttempt = new QuizAttempt(
            quizService.getQuizById(request.quizId()),
            request.score(),
            request.isFirstAttempt(),
            learnerService.getLearnerEntityById(request.learnerId())
        );
        QuizAttempt createdQuizAttempt = quizAttemptRepository.save(quizAttempt);
        return new QuizAttemptResponseDTO(
            createdQuizAttempt.getAttemptId(),
            createdQuizAttempt.getQuiz().getQuizId(),
            createdQuizAttempt.getScore(),
            createdQuizAttempt.getIsFirstAttempt(),
            createdQuizAttempt.getAttemptedAt(),
            createdQuizAttempt.getLearner().getId()
        );
    }
    
    public QuizAttemptResponseDTO updateQuizAttempt(Integer id, QuizAttemptUpdateDTO request){
        if(request == null || request.learnerId() == null || request.quizId() == null || id == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz attempt id or request cannot be null");}
        QuizAttempt existingQuizAttempt = quizAttemptRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz Attempt not found"));
        existingQuizAttempt.setQuiz(quizService.getQuizById(request.quizId()));
        existingQuizAttempt.setScore(request.score());
        existingQuizAttempt.setIsFirstAttempt(request.isFirstAttempt());
        existingQuizAttempt.setAttemptedAt(request.attemptedAt());
        existingQuizAttempt.setLearner(learnerService.getLearnerEntityById(request.learnerId()));
        
        QuizAttempt savedQuizAttempt = quizAttemptRepository.save(existingQuizAttempt);
        
        return new QuizAttemptResponseDTO(
            savedQuizAttempt.getAttemptId(),
            savedQuizAttempt.getQuiz().getQuizId(),
            savedQuizAttempt.getScore(),
            savedQuizAttempt.getIsFirstAttempt(),
            savedQuizAttempt.getAttemptedAt(),
            savedQuizAttempt.getLearner().getId()
        );
    }
    
    public void deleteQuizAttempt(Integer id){
        if(id == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz attempt id cannot be null");}
        QuizAttempt existingQuizAttempt = quizAttemptRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz Attempt not found"));
        quizAttemptRepository.delete(existingQuizAttempt);
    }
}
