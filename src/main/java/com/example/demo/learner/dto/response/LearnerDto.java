package com.example.demo.learner.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record LearnerDto(
        UUID id,
        String username,
        OffsetDateTime createdAt,
        short totalPoints,
        boolean isContributor
) {}
