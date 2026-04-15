package com.marketplace.identity.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record UserDto(
    UUID id,
    String email,
    String phone,
    String status,
    Boolean emailVerified,
    Boolean phoneVerified,
    Set<String> roles,
    Instant createdAt
) {}