package com.cballestas.gestion_matriculas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class RegistroMatriculaDTO {

    private String id;

    @NotNull(message = "Debe ingresar la fecha de matricula - Ej: 2024-10-27T11:09:30")
    @JsonProperty(value = "fecha_matricula")
    private LocalDateTime fechaMatricula;

    @NotNull(message = "Estudiante es requerido para registrar la matrícula")
    private EstudianteDTO estudiante;

    @NotNull(message = "Debe ingresar la lista de cursos para realizar la matrícula")
    @Size(min = 1, message = "Debe existir al menos un curso para relizar la matricula")
    private List<CursoDTO> cursos;

    private Boolean estado;
}
