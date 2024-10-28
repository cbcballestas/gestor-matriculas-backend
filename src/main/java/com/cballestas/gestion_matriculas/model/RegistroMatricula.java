package com.cballestas.gestion_matriculas.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "registro_matriculas")
@Builder
public class RegistroMatricula {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field(name = "fecha_matricula")
    private LocalDateTime fechaMatricula;

    @Field
    private Estudiante estudiante;

    @Field
    private List<Curso> cursos;

    @Field
    private Boolean estado;
}
