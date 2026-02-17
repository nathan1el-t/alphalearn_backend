package com.example.demo.quizquestion;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.quiz.QuizService;
import com.example.demo.quizquestion.dto.QuizQuestionCreateDTO;
import com.example.demo.quizquestion.dto.QuizQuestionResponseDTO;

@Service
public class QuizQuestionService {
    private final QuizService quizService;
    private final QuizQuestionRepository quizQuestionRepository;

    public QuizQuestionService(QuizQuestionRepository quizQuestionRepository, QuizService quizService){
        this.quizService = quizService;
        this.quizQuestionRepository = quizQuestionRepository;
    }

    public List<QuizQuestionResponseDTO> getAllQuizQuestions(){
        return quizQuestionRepository.findAll()
                .stream()
                .map(quizQuestion -> new QuizQuestionResponseDTO(
                    quizQuestion.getQuestionId(),
                    quizQuestion.getQuiz().getQuizId(),
                    quizQuestion.getQuestionText(),
                    quizQuestion.getQuizQuestionType(),
                    quizQuestion.getCorrectAnswer()
                )).toList();
    }

    public QuizQuestionResponseDTO getQuestionById(Integer id){
        if(id == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question id cannot be null");}
        QuizQuestion quizQuestion = quizQuestionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz Question not found"));
        return new QuizQuestionResponseDTO(
            quizQuestion.getQuestionId(),
            quizQuestion.getQuiz().getQuizId(),
            quizQuestion.getQuestionText(),
            quizQuestion.getQuizQuestionType(),
            quizQuestion.getCorrectAnswer()
        );
    }
    
    public QuizQuestion getQuestionEntityById(Integer id){
        if(id == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question id cannot be null");}
        QuizQuestion quizQuestion = quizQuestionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz Question not found"));
        return quizQuestion;
    }
    
    public QuizQuestionResponseDTO createQuizQuestion(QuizQuestionCreateDTO request){
        if(request == null || request.quizId() == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question request cannot be null");}
        QuizQuestion quizQuestion = new QuizQuestion(request.questionText(), request.quizQuestionType(), request.correctAnswer(), quizService.getQuizById(request.quizId()));
        QuizQuestion createdQuizQuestion = quizQuestionRepository.save(quizQuestion);
        return new QuizQuestionResponseDTO(
            createdQuizQuestion.getQuestionId(),
            createdQuizQuestion.getQuiz().getQuizId(),
            createdQuizQuestion.getQuestionText(),
            createdQuizQuestion.getQuizQuestionType(),
            createdQuizQuestion.getCorrectAnswer()
        );
    }
    
    public QuizQuestionResponseDTO updateQuizQuestion(Integer id, QuizQuestionCreateDTO request){
        if(request == null || request.quizId() == null || id == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question id or request cannot be null");}
        QuizQuestion existingQuizQuestion = quizQuestionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz Question not found"));
        existingQuizQuestion.setQuestionText(request.questionText());
        existingQuizQuestion.setQuizQuestionType(request.quizQuestionType());
        existingQuizQuestion.setCorrectAnswer(request.correctAnswer());
        existingQuizQuestion.setQuiz(quizService.getQuizById(request.quizId()));
        QuizQuestion savedQuizQuestion = quizQuestionRepository.save(existingQuizQuestion);
        return new QuizQuestionResponseDTO(
            savedQuizQuestion.getQuestionId(),
            savedQuizQuestion.getQuiz().getQuizId(),
            savedQuizQuestion.getQuestionText(),
            savedQuizQuestion.getQuizQuestionType(),
            savedQuizQuestion.getCorrectAnswer()
        );
    }
    
    public void deleteQuizQuestion(Integer id){
        if(id == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question id cannot be null");}
        QuizQuestion existingQuizQuestion = quizQuestionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz Question not found"));
        quizQuestionRepository.delete(existingQuizQuestion);
    }
}
