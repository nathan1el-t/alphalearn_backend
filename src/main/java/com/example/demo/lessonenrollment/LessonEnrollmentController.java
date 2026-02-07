package com.example.demo.lessonenrollment;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lessonenrollments")
public class LessonEnrollmentController {

    private final LessonEnrollmentService service;

    public LessonEnrollmentController(LessonEnrollmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<LessonEnrollment> getEnrollments() {
        return service.getAllEnrollments();
    }
}
