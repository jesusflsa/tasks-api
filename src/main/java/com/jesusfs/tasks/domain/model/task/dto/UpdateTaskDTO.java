package com.jesusfs.tasks.domain.model.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateTaskDTO(
        @NotBlank
        @Size(min = 3, message = "El título debe tener al menos 3 caracteres.")
        String title,
        String description
) {}
