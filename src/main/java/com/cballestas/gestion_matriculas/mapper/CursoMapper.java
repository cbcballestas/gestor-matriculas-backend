package com.cballestas.gestion_matriculas.mapper;

import com.cballestas.gestion_matriculas.dto.CursoDTO;
import com.cballestas.gestion_matriculas.model.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CursoMapper {

    Curso toEntity(CursoDTO cursoDTO);

    CursoDTO toDTO(Curso curso);
}
