package com.jesusfs.tasks.domain.model.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(
        @Min(value = 4, message = "El nombre de usuario debe tener mínimo 4 caracteres.")
        @NotBlank
        String username,

        @Email
        @NotBlank
        String email
) {}
