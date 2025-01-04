package com.jesusfs.tasks.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UserNotValidException.class)
    public ResponseEntity<List<ErrorEntity>> handleUserNotValid(List<ObjectError> exceptions) {
        return ResponseEntity.badRequest().body(exceptions.stream().map(ErrorEntity::new).toList());
    }

    public record ErrorEntity(String field, String message) {
        public ErrorEntity(ObjectError error) {
            this(error.getObjectName(), error.getDefaultMessage());
        }
    }
}
