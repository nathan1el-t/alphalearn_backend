package com.example.demo.quizquestion;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quizquestions")
public class QuizQuestionController {
    private final QuizQuestionService quizQuestionService;

    public QuizQuestionController(QuizQuestionService quizQuestionService){
        this.quizQuestionService = quizQuestionService;
    }

    @GetMapping
    public List<QuizQuestionResponseDTO> getAllQuizQuestions(){
        return quizQuestionService.getAllQuizQuestions();
    }

    @GetMapping("/{id}")
    public QuizQuestionResponseDTO getQuizQuestionById(@PathVariable Integer id){
        return quizQuestionService.getQuestionById(id);
    }
}
