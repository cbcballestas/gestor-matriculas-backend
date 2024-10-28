package com.cballestas.gestion_matriculas.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ModelNotFoundException extends RuntimeException {

    private HttpStatus status;

    public ModelNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
