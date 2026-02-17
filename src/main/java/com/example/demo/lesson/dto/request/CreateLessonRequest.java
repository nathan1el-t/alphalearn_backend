package com.example.demo.lesson.dto.request;

import java.util.UUID;

public record CreateLessonRequest(
        String title,
        String learningObjectives,
        Object content,
        Integer conceptId,
        UUID contributorId,
        Boolean submit
) {}
