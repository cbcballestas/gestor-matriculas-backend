package com.cballestas.gestion_matriculas.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class BaseErrorResponse {
    private int statusCode;
}
