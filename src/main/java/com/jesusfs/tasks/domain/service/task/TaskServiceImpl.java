package com.jesusfs.tasks.domain.service.task;

import com.jesusfs.tasks.domain.model.task.TaskModel;
import com.jesusfs.tasks.domain.model.task.dto.RequestTaskDTO;
import com.jesusfs.tasks.domain.model.task.dto.UpdateTaskDTO;
import com.jesusfs.tasks.domain.model.task.status.ResponseStatusDTO;
import com.jesusfs.tasks.domain.model.task.status.TaskStatus;
import com.jesusfs.tasks.domain.repository.TaskRepository;
import com.jesusfs.tasks.domain.service.user.UserService;
import com.jesusfs.tasks.exceptions.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskModel createTask(RequestTaskDTO taskDTO) {
        log.info("TaskServiceImpl::createTask execution started.");
        log.debug("TaskServiceImpl::createTask params provided: {}", taskDTO);
        TaskModel task = new TaskModel();
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        if (taskDTO.expiresAt() != null) {
            task.setExpiresAt(taskDTO.expiresAt().toInstant(ZoneOffset.UTC));
        }
        if (taskDTO.status() != null) {
            task.setStatus(taskDTO.status());
        }
        task.setAuthor(userService.getUser());

        log.info("TaskServiceImpl::createTask execution ended.");
        return taskRepository.save(task);
    }

    @Override
    public TaskModel updateTask(Long id, UpdateTaskDTO taskDTO) {
        log.info("TaskServiceImpl::updateTask execution started.");
        TaskModel task = getTaskById(id);
        log.info("TaskServiceImpl::updateTask params provided for task {}: {}.", id, taskDTO);
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setUpdatedAt(Instant.now());
        task.setStatus(taskDTO.status());
        if (taskDTO.expiresAt() != null) task.setExpiresAt(taskDTO.expiresAt().toInstant(ZoneOffset.UTC));

        log.info("TaskServiceImpl::updateTask execution ended.");
        return taskRepository.save(task);
    }

    @Override
    public TaskModel getTaskById(Long id) {
        log.info("TaskServiceImpl::getTaskById execution started.");
        log.debug("TaskServiceImpl::getTaskById id provided: {}", id);
        log.info("TaskServiceImpl::getTaskById execution ended.");
        return taskRepository.findByIdAndAuthor(id, userService.getUser()).orElseThrow(() -> {
            log.error("Task with id {} not exists.", id);
            return new TaskNotFoundException("Task with id " + id + " not exists.");
        });
    }

    @Override
    public Page<TaskModel> getTaskList(Pageable page) {
        log.info("TaskServiceImpl::getTaskList execution started.");
        log.info("TaskServiceImpl::getTaskList execution ended.");
        return taskRepository.findByAuthor(userService.getUser(), page);
    }

    @Override
    public void deleteTask(Long id) {
        log.info("TaskServiceImpl::deleteTask execution started.");
        log.debug("TaskServiceImpl::deleteTask id provided: {}", id);
        TaskModel task = getTaskById(id);
        log.info("TaskServiceImpl::deleteTask execution ended.");
        taskRepository.delete(task);
    }

    @Override
    @Cacheable("status")
    public List<ResponseStatusDTO> getStatus() {
        log.info("TaskServiceImpl::getStatus started ended.");
        List<ResponseStatusDTO> statusList = Arrays.stream(TaskStatus.values()).map(status -> new ResponseStatusDTO(status.name(), status.getValue())).collect(Collectors.toList());
        log.info("TaskServiceImpl::getStatus execution ended.");
        return statusList;
    }
}
