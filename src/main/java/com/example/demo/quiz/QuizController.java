package com.example.demo.quiz;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.quiz.dto.QuizCreateDTO;
import com.example.demo.quiz.dto.QuizResponseDTO;

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

    @PostMapping
    public Quiz createQuiz(@RequestBody QuizCreateDTO request){
        return quizService.createQuiz(request); //find a way to clean up json that is returned after post
    }
}