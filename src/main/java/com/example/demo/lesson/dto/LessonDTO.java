package com.example.demo.lesson.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

public record LessonDTO(
        Integer lessonId,
        String title,
        JsonNode content,
        String moderationStatus,
        UUID contributorId,
        OffsetDateTime createdAt
) {}
