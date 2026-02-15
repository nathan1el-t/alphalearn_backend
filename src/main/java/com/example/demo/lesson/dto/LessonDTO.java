package com.example.demo.lesson.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record LessonDTO(
        Integer lessonId,
        String title,
        String learningObjectives,
        Object content,
        String moderationStatus,
        UUID contributorId,
        OffsetDateTime createdAt
) {}
