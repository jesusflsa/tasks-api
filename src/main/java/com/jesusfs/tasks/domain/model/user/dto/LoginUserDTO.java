package com.jesusfs.tasks.domain.model.user.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginUserDTO(
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
