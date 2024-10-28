package com.cballestas.gestion_matriculas.exception;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ErrorResponse extends BaseErrorResponse{
    private String message;
}
