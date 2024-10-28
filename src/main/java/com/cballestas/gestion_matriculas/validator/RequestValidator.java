package com.cballestas.gestion_matriculas.validator;

import com.cballestas.gestion_matriculas.exception.ValidationErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestValidator {
    private final Validator validator;

    public <T> Mono<T> validate(T object) {
        if (object == null) return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Errors errors = new BeanPropertyBindingResult(object, object.getClass().getSimpleName());
        validator.validate(object, errors);

        if (!errors.hasErrors()) return Mono.just(object);

        // Se obtienen lista de erorres
        Set<String> errorMessages = errors.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toSet());

        return Mono.error(new ValidationErrorException(errorMessages, HttpStatus.BAD_REQUEST));
    }
}
