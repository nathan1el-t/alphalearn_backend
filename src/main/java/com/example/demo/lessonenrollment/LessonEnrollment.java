package com.example.demo.lessonenrollment;

import java.time.OffsetDateTime;

import com.example.demo.learner.Learner;
import com.example.demo.lesson.Lesson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

@JsonIgnoreProperties({"profile"}) //temporary fix to prevent loop, to review in the future
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "lesson_enrollments")
public class LessonEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    @Setter(lombok.AccessLevel.NONE)
    private Integer enrollmentId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "learner_id", nullable = false)
    private Learner learner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private boolean completed;

    @Column(name = "first_completed_at")
    private OffsetDateTime firstCompletedAt;
}