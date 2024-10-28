package com.cballestas.gestion_matriculas.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Getter
public class ValidationErrorException extends RuntimeException {

    private HttpStatus status;
    private Set<String> errors;

    public ValidationErrorException(String message) {
        super(message);
    }

    public ValidationErrorException(Set<String> errors, HttpStatus status) {
        this.errors = errors;
        this.status = status;
    }
}