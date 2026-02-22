package com.example.demo.admin.dto.response;

import java.util.UUID;

public record UserRoleDto(
        UUID userId,
        String role
) {}
