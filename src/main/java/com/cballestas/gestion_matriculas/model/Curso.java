package com.cballestas.gestion_matriculas.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "cursos")
@Builder
public class Curso {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private String nombre;

    @Field
    private String siglas;

    @Field
    private Boolean estado;
}
