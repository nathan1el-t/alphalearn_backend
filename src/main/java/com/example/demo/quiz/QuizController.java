package com.example.demo.quiz;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }

    @GetMapping
    public List<QuizResponseDTO> getQuizzes(){
        return quizService.getAllQuizzes();
    }

    @GetMapping("/{id}")
    public QuizResponseDTO getQuizById(@PathVariable Integer id){
        return quizService.getQuizById(id);
    }
}