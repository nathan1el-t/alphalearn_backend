package com.example.demo.contributor.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ContributorDto(
        UUID contributorId,
        OffsetDateTime createdAt
) {}
