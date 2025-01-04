package com.jesusfs.tasks.domain.model.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserDTO(
        @Email
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
