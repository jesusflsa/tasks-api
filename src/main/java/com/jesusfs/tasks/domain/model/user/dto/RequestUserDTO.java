package com.jesusfs.tasks.domain.model.user.dto;

import jakarta.validation.constraints.*;

public record RequestUserDTO(
        @Size(min = 4, message = "El nombre de usuario debe tener mínimo 4 caracteres.")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Ingresa un nombre de usuario válido.")
        @NotBlank
        String username,

        @Email
        @NotBlank
        String email,

        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$",
                message = "La contraseña debe tener al menos 6 caracteres, una letra mayúscula, una letra minúscula y al menos un número."
        )
        @NotBlank
        String password

) {
}
