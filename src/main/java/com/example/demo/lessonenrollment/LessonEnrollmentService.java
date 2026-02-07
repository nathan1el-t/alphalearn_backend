package com.example.demo.lessonenrollment;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LessonEnrollmentService {

    private final LessonEnrollmentRepository repository;

    public LessonEnrollmentService(LessonEnrollmentRepository repository) {
        this.repository = repository;
    }

    public List<LessonEnrollment> getAllEnrollments() {
        return repository.findAll();
    }
}
