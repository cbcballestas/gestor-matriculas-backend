package com.cballestas.gestion_matriculas.exception;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ErrorsResponse extends BaseErrorResponse {
    private Set<String> errors;
}
