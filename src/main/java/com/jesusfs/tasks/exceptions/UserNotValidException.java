package com.jesusfs.tasks.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Setter
public class UserNotValidException extends RuntimeException {
    private List<FieldError> errors;
    public UserNotValidException(List<FieldError> errors) {
        super();
        this.errors = errors;
    }
}
