package com.example.demo.learner.dto.response;

import java.util.UUID;

public record LearnerContributorRoleDto(
        UUID learnerId,
        boolean isContributor
) {}
