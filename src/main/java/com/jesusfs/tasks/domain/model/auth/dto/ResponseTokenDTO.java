package com.jesusfs.tasks.domain.model.auth.dto;

public record ResponseTokenDTO(
    String token,
    String refreshToken,
    Integer expires
) {}
