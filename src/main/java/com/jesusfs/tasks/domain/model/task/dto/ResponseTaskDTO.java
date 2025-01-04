package com.jesusfs.tasks.domain.model.task.dto;

import com.jesusfs.tasks.domain.model.task.TaskModel;

import java.time.Instant;

public record ResponseTaskDTO(
        Long id,
        String title,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
    public ResponseTaskDTO(TaskModel task) {
        this(task.getId(), task.getTitle(), task.getDescription(), task.getCreatedAt(), task.getUpdatedAt());
    }
}
