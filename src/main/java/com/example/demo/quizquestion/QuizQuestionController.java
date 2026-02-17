package com.example.demo.quizquestion;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.quizquestion.dto.QuizQuestionCreateDTO;
import com.example.demo.quizquestion.dto.QuizQuestionResponseDTO;

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

    @PostMapping
    public QuizQuestionResponseDTO createQuizQuestion(@RequestBody QuizQuestionCreateDTO request){
        return quizQuestionService.createQuizQuestion(request);
    }

    @PutMapping("/{id}")
    public QuizQuestionResponseDTO updateQuizQuestion(@PathVariable Integer id, @RequestBody QuizQuestionCreateDTO request){
        return quizQuestionService.updateQuizQuestion(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteQuizQuestion(@PathVariable Integer id){
        quizQuestionService.deleteQuizQuestion(id);
    }
}
