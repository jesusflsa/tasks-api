package com.jesusfs.tasks.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UserNotValidException.class)
    public ResponseEntity<List<ErrorEntity>> handleUserNotValid(List<FieldError> exceptions) {
        return ResponseEntity.badRequest().body(exceptions.stream().map(ErrorEntity::new).toList());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorEntity>>  handleArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorEntity> errors = ex.getFieldErrors().stream().map(ErrorEntity::new).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);

    }

    public record ErrorEntity(String field, String message) {
        public ErrorEntity(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
