package com.example.demo.lesson.dto.request;

public record UpdateLessonRequest(
        String title,
        Object content
) {}
