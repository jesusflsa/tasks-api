package com.jesusfs.tasks.domain.repository;

import com.jesusfs.tasks.domain.model.task.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {
}
