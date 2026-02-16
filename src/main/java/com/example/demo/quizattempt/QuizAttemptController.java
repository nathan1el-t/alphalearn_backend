package com.example.demo.quizattempt;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.quizattempt.dto.QuizAttemptCreateDTO;
import com.example.demo.quizattempt.dto.QuizAttemptResponseDTO;
import com.example.demo.quizattempt.dto.QuizAttemptUpdateDTO;

@RestController
@RequestMapping("/api/quizattempts")
public class QuizAttemptController {
    private final QuizAttemptService quizAttemptService;

    public QuizAttemptController(QuizAttemptService quizAttemptService){
        this.quizAttemptService = quizAttemptService;
    }

    @GetMapping
    public List<QuizAttemptResponseDTO> getAllQuizAttempts(){
        return quizAttemptService.getAllQuizAttempts();
    }

    @GetMapping("/{id}")
    public QuizAttemptResponseDTO getQuizAttemptById(@PathVariable Integer id){
        return quizAttemptService.getQuizAttemptById(id);
    }

    @PostMapping()
    public QuizAttempt createQuizAttempt(@RequestBody QuizAttemptCreateDTO request){
        return quizAttemptService.createQuizAttempt(request);
    }

    @PutMapping("/{id}")
    public QuizAttemptResponseDTO updateQuizAttempt(@PathVariable Integer id, @RequestBody QuizAttemptUpdateDTO request){
        return quizAttemptService.updateQuizAttempt(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteQuizAttempt(@PathVariable Integer id){
        quizAttemptService.deleteQuizAttempt(id);
    }
}
