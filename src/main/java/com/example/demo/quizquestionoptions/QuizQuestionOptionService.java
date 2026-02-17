package com.example.demo.quizquestionoptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.quizquestion.QuizQuestion;
import com.example.demo.quizquestion.QuizQuestionService;
import com.example.demo.quizquestionoptions.dto.QuizQuestionOptionCreateDTO;
import com.example.demo.quizquestionoptions.dto.QuizQuestionOptionResponseDTO;

@Service
public class QuizQuestionOptionService {
    private final QuizQuestionService quizQuestionService;
    private final QuizQuestionOptionRepository quizQuestionOptionRepository;

    public QuizQuestionOptionService(QuizQuestionService quizQuestionService, QuizQuestionOptionRepository quizQuestionOptionRepository){
        this.quizQuestionService = quizQuestionService;
        this.quizQuestionOptionRepository = quizQuestionOptionRepository;
    }

    public List<QuizQuestionOptionResponseDTO> getAllQuestionOptions(){
        return quizQuestionOptionRepository.findAll()
                .stream()
                .map(quizQuestionOption -> new QuizQuestionOptionResponseDTO(
                    quizQuestionOption.getOptionId(),
                    quizQuestionOption.getQuizQuestion().getQuestionId(),
                    quizQuestionOption.getOptionKey(),
                    quizQuestionOption.getOptionText(),
                    quizQuestionOption.getCreatedAt()
                )).toList();
    }

    public QuizQuestionOptionResponseDTO getQuestionOptionById(Integer id){
        if(id == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question option id cannot be null");}
        QuizQuestionOption questionOption = quizQuestionOptionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question Option not found"));
        return new QuizQuestionOptionResponseDTO(
            questionOption.getOptionId(),
            questionOption.getQuizQuestion().getQuestionId(),
            questionOption.getOptionKey(),
            questionOption.getOptionText(),
            questionOption.getCreatedAt()
        );
    }
    
    public QuizQuestionOptionResponseDTO createQuestionOption(QuizQuestionOptionCreateDTO request){
        if(request == null || request.questionId() == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question option request cannot be null");}
        QuizQuestion question = quizQuestionService.getQuestionEntityById(request.questionId());
        QuizQuestionOption questionOption = new QuizQuestionOption(
            question,
            request.optionKey(),
            request.optionText()
        );
        
        quizQuestionOptionRepository.save(questionOption);
        
        return new QuizQuestionOptionResponseDTO(
            questionOption.getOptionId(),
            questionOption.getQuizQuestion().getQuestionId(),
            questionOption.getOptionKey(),
            questionOption.getOptionText(),
            questionOption.getCreatedAt()
        );
    }
    
    public QuizQuestionOptionResponseDTO updateQuestionOption(Integer id, QuizQuestionOptionCreateDTO request){
        if(id == null || request == null || request.questionId() == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question option id or request cannot be null");}
        QuizQuestionOption existingQuestionOption = quizQuestionOptionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question Option not found"));
        existingQuestionOption.setQuizQuestion(quizQuestionService.getQuestionEntityById(request.questionId()));
        existingQuestionOption.setOptionKey(request.optionKey());
        existingQuestionOption.setOptionText(request.optionText());
        QuizQuestionOption savedQuestionOption = quizQuestionOptionRepository.save(existingQuestionOption);
        return new QuizQuestionOptionResponseDTO(
            savedQuestionOption.getOptionId(),
            savedQuestionOption.getQuizQuestion().getQuestionId(),
            savedQuestionOption.getOptionKey(),
            savedQuestionOption.getOptionText(),
            savedQuestionOption.getCreatedAt()
        );
    }
    
    public void deleteQuestionOption(Integer id){
        if(id == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question option id cannot be null");}
        QuizQuestionOption existingQuestionOption = quizQuestionOptionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question Option not found"));
        quizQuestionOptionRepository.delete(existingQuestionOption);
    }
}
