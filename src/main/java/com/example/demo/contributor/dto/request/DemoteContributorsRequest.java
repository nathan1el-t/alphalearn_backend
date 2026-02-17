package com.example.demo.contributor.dto.request;

import java.util.List;
import java.util.UUID;

public record DemoteContributorsRequest(
        List<UUID> contributorIds
) {}
