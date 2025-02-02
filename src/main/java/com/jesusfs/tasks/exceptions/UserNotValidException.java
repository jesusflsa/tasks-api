package com.jesusfs.tasks.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotValidException extends RuntimeException {
    private List<FieldError> errors;
    public UserNotValidException(List<FieldError> errors) {
        super();
        this.errors = errors;
    }
}
