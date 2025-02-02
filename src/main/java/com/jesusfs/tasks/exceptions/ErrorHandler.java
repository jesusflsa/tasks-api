package com.jesusfs.tasks.exceptions;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Hidden
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UserNotValidException.class)
    public ResponseEntity<List<ErrorEntity>> handleUserNotValid(UserNotValidException ex) {
        List<FieldError> exceptions = ex.getErrors();
        return ResponseEntity.badRequest().body(exceptions.stream().map(ErrorEntity::new).toList());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorEntity>> handleArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorEntity> errors = ex.getFieldErrors().stream().map(ErrorEntity::new).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorEntity> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.badRequest().body(new ErrorEntity("user", ex.getMessage()));
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorEntity> handleTaskNotFound(TaskNotFoundException ex) {
        return ResponseEntity.badRequest().body(new ErrorEntity("task", ex.getMessage()));
    }

    public record ErrorEntity(String field, String message) {
        public ErrorEntity(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
