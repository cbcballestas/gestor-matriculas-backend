package com.cballestas.gestion_matriculas.mapper;

import com.cballestas.gestion_matriculas.dto.EstudianteDTO;
import com.cballestas.gestion_matriculas.model.Estudiante;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EstudianteMapper {

    Estudiante toEntity(EstudianteDTO estudianteDTO);

    EstudianteDTO toDTO(Estudiante estudiante);
}
