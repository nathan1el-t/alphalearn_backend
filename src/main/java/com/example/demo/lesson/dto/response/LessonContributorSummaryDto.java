package com.example.demo.lesson.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record LessonContributorSummaryDto(
        Integer lessonId,
        String title,
        String moderationStatus,
        UUID contributorId,
        OffsetDateTime createdAt
) {}
