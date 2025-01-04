package com.jesusfs.tasks.domain.repository;

import com.jesusfs.tasks.domain.model.task.TaskModel;
import com.jesusfs.tasks.domain.model.user.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    Optional<TaskModel> findByIdAndAuthor(Long id, UserModel author);
    Page<TaskModel> findByAuthor(UserModel author, Pageable page);
}
