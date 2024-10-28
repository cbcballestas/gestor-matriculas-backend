package com.cballestas.gestion_matriculas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class EstudianteDTO {

    private String id;

    @NotNull(message = "Nombres son requeridos")
    private String nombres;

    @NotNull(message = "Apellidos son requeridos")
    private String apellidos;

    @NotNull(message = "Debe ingresar un DNI")
    private String dni;

    private Integer edad;
}
