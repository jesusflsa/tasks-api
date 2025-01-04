package com.jesusfs.tasks.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class UserNotValidException extends RuntimeException {
    private List<ObjectError> errors;
    public UserNotValidException(List<ObjectError> errors) {
        super();
        this.errors = errors;
    }
}
