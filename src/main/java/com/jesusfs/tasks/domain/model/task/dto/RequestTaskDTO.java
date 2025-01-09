package com.jesusfs.tasks.domain.model.task.dto;

import com.jesusfs.tasks.domain.model.task.status.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record RequestTaskDTO(
        @NotBlank
        @Size(min = 3, message = "El título debe tener al menos 3 caracteres.")
        String title,
        String description,
        TaskStatus status,
        @Future
        LocalDateTime expiresAt
) {}
