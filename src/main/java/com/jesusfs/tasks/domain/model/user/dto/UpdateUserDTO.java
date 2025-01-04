package com.jesusfs.tasks.domain.model.user.dto;

import jakarta.validation.constraints.*;

public record UpdateUserDTO(
        @Size(min = 4, message = "El nombre de usuario debe tener mínimo 4 caracteres.")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Ingresa un nombre de usuario válido.")
        @NotBlank
        String username,

        @Email
        @NotBlank
        String email
) {}
