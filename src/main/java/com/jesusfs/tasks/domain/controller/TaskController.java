package com.jesusfs.tasks.domain.controller;

import com.jesusfs.tasks.domain.model.task.TaskModel;
import com.jesusfs.tasks.domain.model.task.dto.RequestTaskDTO;
import com.jesusfs.tasks.domain.model.task.dto.ResponseTaskDTO;
import com.jesusfs.tasks.domain.model.task.dto.UpdateTaskDTO;
import com.jesusfs.tasks.domain.service.task.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/task")
@PreAuthorize("isAuthenticated()")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/new")
    public ResponseEntity<ResponseTaskDTO> createTask(@RequestBody RequestTaskDTO taskDTO) {
        log.info("TaskController::createTask execution started.");
        TaskModel task = taskService.createTask(taskDTO);
        log.info("TaskController::createTask execution ended.");
        return new ResponseEntity<>(new ResponseTaskDTO(task), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseTaskDTO>> getTasks(@PageableDefault(size = 5) Pageable page) {
        log.info("TaskController::getTasks execution started.");
        Page<TaskModel> tasks = taskService.getTaskList(page);
        log.info("TaskController::getTasks execution ended.");
        return ResponseEntity.ok(tasks.map(ResponseTaskDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTaskDTO> getTaskById(@PathVariable Long id) {
        log.info("TaskController::getTaskById execution started.");
        TaskModel task = taskService.getTaskById(id);
        log.info("TaskController::getTaskById execution ended.");
        return ResponseEntity.ok(new ResponseTaskDTO(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        log.info("TaskController::deleteTask execution started.");
        taskService.deleteTask(id);
        log.info("TaskController::deleteTask execution ended.");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTaskDTO> updateTask(@PathVariable Long id, @RequestBody UpdateTaskDTO taskDTO) {
        log.info("TaskController::updateTask execution started.");
        TaskModel task = taskService.updateTask(id, taskDTO);
        log.info("TaskController::updateTask execution ended.");
        return ResponseEntity.ok(new ResponseTaskDTO(task));
    }
}
