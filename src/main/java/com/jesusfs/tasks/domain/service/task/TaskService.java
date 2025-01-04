package com.jesusfs.tasks.domain.service.task;

import com.jesusfs.tasks.domain.model.task.TaskModel;
import com.jesusfs.tasks.domain.model.task.dto.RequestTaskDTO;
import com.jesusfs.tasks.domain.model.task.dto.UpdateTaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskModel createTask(RequestTaskDTO taskDTO);
    TaskModel updateTask(Long id, UpdateTaskDTO taskDTO);
    TaskModel getTaskById(Long id);
    Page<TaskModel> getTaskList(Pageable page);
    void deleteTask(Long id);
}
