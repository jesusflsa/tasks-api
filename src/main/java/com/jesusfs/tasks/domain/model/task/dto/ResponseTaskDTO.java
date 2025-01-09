package com.jesusfs.tasks.domain.model.task.dto;

import com.jesusfs.tasks.domain.model.task.TaskModel;

import java.time.Instant;
import java.time.LocalDateTime;

public record ResponseTaskDTO(
        Long id,
        String title,
        String description,
        String status,
        Instant createdAt,
        Instant updatedAt,
        Instant expiresAt
) {
    public ResponseTaskDTO(TaskModel task) {
        this(task.getId(), task.getTitle(), task.getDescription(), task.getStatus().name(), task.getCreatedAt(), task.getUpdatedAt(), task.getExpiresAt());
    }
}
