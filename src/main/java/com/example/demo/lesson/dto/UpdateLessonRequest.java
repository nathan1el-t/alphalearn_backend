package com.example.demo.lesson.dto;

public record UpdateLessonRequest(
        String title,
        String learningObjectives,
        Object content
) {}
