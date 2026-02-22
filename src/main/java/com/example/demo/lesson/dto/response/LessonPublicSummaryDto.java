package com.example.demo.lesson.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record LessonPublicSummaryDto(
        Integer lessonId,
        String title,
        UUID contributorId,
        OffsetDateTime createdAt
) {}
