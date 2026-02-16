package com.example.demo.quizattempt;

import java.util.List;

import org.springframework.stereotype.Service;

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
        QuizAttempt quizAttempt = quizAttemptRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz attempt not found"));
        return new QuizAttemptResponseDTO(
            quizAttempt.getAttemptId(),
            quizAttempt.getQuiz().getQuizId(),
            quizAttempt.getScore(),
            quizAttempt.getIsFirstAttempt(),
            quizAttempt.getAttemptedAt(),
            quizAttempt.getLearner().getId()
        );
    }

    public QuizAttempt createQuizAttempt(QuizAttemptCreateDTO request){
        QuizAttempt quizAttempt = new QuizAttempt(
            quizService.getQuizById(request.quizId()),
            request.score(),
            request.isFirstAttempt(),
            learnerService.getLearnerEntityById(request.learnerId())
        );
        return quizAttemptRepository.save(quizAttempt);
    }

    public QuizAttemptResponseDTO updateQuizAttempt(Integer id, QuizAttemptUpdateDTO request){
        QuizAttempt existingQuizAttempt = quizAttemptRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz attempt not found"));
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
        QuizAttempt existingQuizAttempt = quizAttemptRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz attempt not found"));
        quizAttemptRepository.delete(existingQuizAttempt);
    }
}
