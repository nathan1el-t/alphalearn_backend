package com.example.demo.lesson.dto.request;

public record UpdateLessonRequest(
        String title,
        String learningObjectives,
        Object content
) {}
