package com.example.demo.admin.dto.response;

import java.util.UUID;

import com.example.demo.lesson.enums.LessonModerationStatus;

public record AdminLessonDetailDto(
    UUID contributorId,
    Integer lessonId,
    String lessonTitle,
    LessonModerationStatus lessonModerationStatus
) {}
