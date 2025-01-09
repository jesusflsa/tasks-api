package com.jesusfs.tasks.domain.model.task.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskStatus {
    TO_DO("To do"),
    IN_PROGRESS("In progress"),
    CANCELLED("Cancelled"),
    COMPLETED("Completed");

    private final String value;

}
