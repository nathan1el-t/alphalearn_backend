package com.example.demo.quizattempt;

import java.time.OffsetDateTime;

import com.example.demo.learner.Learner;
import com.example.demo.quiz.Quiz;

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

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "quiz_attempts")
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attempt_id")
    private Integer attemptId;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "score")
    private Integer score;

    @Column(name = "is_first_attempt")
    private boolean isFirstAttempt;

    @Column(name = "attempted_at")
    private OffsetDateTime attemptedAt;

    @ManyToOne
    @JoinColumn(name = "learner_id", nullable = false)
    private Learner learner; 

}
