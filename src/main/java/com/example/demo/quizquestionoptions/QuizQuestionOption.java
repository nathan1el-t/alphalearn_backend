package com.example.demo.quizquestionoptions;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.quizquestion.QuizQuestion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "quiz_question_options")
public class QuizQuestionOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Integer optionId;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuizQuestion quizQuestion;   

    @Column(name = "option_key")
    private String optionKey;

    @Column(name = "option_text")
    private String optionText;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    public QuizQuestionOption(QuizQuestion quizQuestion, String optionKey, String optionText){
        this.quizQuestion = quizQuestion;
        this.optionKey = optionKey;
        this.optionText = optionText;
    }
}
