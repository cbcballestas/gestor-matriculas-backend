package com.cballestas.gestion_matriculas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CursoDTO {

    private String id;

    @NotNull(message = "Debe ingresar un nombre")
    private String nombre;

    @NotNull(message = "La siglas son requeridas")
    private String siglas;

    private Boolean estado;
}
