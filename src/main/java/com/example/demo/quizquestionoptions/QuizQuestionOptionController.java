package com.example.demo.quizquestionoptions;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.quizquestionoptions.dto.QuizQuestionOptionCreateDTO;
import com.example.demo.quizquestionoptions.dto.QuizQuestionOptionResponseDTO;

@RestController
@RequestMapping("/api/quizquestionoptions")
public class QuizQuestionOptionController {
    private final QuizQuestionOptionService quizQuestionOptionService;

    public QuizQuestionOptionController(QuizQuestionOptionService quizQuestionOptionService){
        this.quizQuestionOptionService = quizQuestionOptionService;
    }

    @GetMapping
    public List<QuizQuestionOptionResponseDTO> getAllQuestionOptions(){
        return quizQuestionOptionService.getAllQuestionOptions();
    }

    @GetMapping("/{id}")
    public QuizQuestionOptionResponseDTO getQuestionOptionById(@PathVariable Integer id){
        return quizQuestionOptionService.getQuestionOptionById(id);
    }

    @PostMapping()
    public QuizQuestionOptionResponseDTO createQuestionOption(@RequestBody QuizQuestionOptionCreateDTO request){
        return quizQuestionOptionService.createQuestionOption(request);
    }

    @PutMapping("/{id}")
    public QuizQuestionOption updateQuestionOption(@PathVariable Integer id, @RequestBody QuizQuestionOptionCreateDTO request){
        return quizQuestionOptionService.updateQuestionOption(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestionOption(@PathVariable Integer id){
        quizQuestionOptionService.deleteQuestionOption(id);
    }
}
