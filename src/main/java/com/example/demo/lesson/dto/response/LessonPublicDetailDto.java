package com.example.demo.lesson.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.demo.lesson.dto.response.LessonDetailView;

public record LessonPublicDetailDto(
        Integer lessonId,
        String title,
        String learningObjectives,
        Object content,
        UUID contributorId,
        OffsetDateTime createdAt
) implements LessonDetailView {}
