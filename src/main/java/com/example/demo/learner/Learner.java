package com.example.demo.learner;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.example.demo.lessonenrollment.LessonEnrollment;
import com.example.demo.quizattempt.QuizAttempt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "learners")
public class Learner {

    @Id
    @Column(columnDefinition= "uuid")
    private UUID id;

    @Setter
    @Column(unique= true)
    private String username;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Setter
    @Column(name = "total_points", nullable = false)
    private Short totalPoints;

    @OneToMany
    @JoinColumn(name = "learner_id")
    private List<QuizAttempt> quizAttempt;

    @OneToMany
    @JoinColumn(name = "learner_id")
    private List<LessonEnrollment> lessonEnrollment;
}
