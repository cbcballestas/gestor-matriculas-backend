package com.cballestas.gestion_matriculas.mapper;

import com.cballestas.gestion_matriculas.dto.RegistroMatriculaDTO;
import com.cballestas.gestion_matriculas.model.RegistroMatricula;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CursoMapper.class, EstudianteMapper.class})
public interface RegistroMatriculaMapper {

    RegistroMatricula toEntity(RegistroMatriculaDTO registroMatriculaDTO);

    @InheritInverseConfiguration
    RegistroMatriculaDTO toDTO(RegistroMatricula registroMatricula);

    List<RegistroMatriculaDTO> toRegistroMatriculaDTOList(List<RegistroMatricula> registroMatriculas);

    List<RegistroMatricula> toRegistroMatriculaList(List<RegistroMatriculaDTO> registroMatriculasDTO);
}
