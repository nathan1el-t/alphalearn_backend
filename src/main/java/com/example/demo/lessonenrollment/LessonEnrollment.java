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

@JsonIgnoreProperties({"profile"}) //temporary fix to prevent loop, to review in the future
@Entity
@Table(name = "lesson_enrollments")
public class LessonEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
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

    // Getters

    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    public Learner getLearner() {
        return learner;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public boolean isCompleted() {
        return completed;
    }

    public OffsetDateTime getFirstCompletedAt() {
        return firstCompletedAt;
    }

    // Setters

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setFirstCompletedAt(OffsetDateTime firstCompletedAt) {
        this.firstCompletedAt = firstCompletedAt;
    }
}
