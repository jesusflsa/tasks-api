package com.jesusfs.tasks.domain.model.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestRefreshDTO(
        @NotBlank
        String refreshToken
) {}
