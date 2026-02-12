package com.example.demo.lesson.dto;

import java.util.UUID;

public record CreateLessonRequest(
        String title,
        String learningObjectives,
        Integer conceptId,
        UUID contributorId,
        Boolean submit
) {}
