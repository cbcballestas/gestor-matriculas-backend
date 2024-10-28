package com.cballestas.gestion_matriculas.service.impl;

import com.cballestas.gestion_matriculas.model.RegistroMatricula;
import com.cballestas.gestion_matriculas.repo.RegistroMatriculaRepository;
import com.cballestas.gestion_matriculas.repo.GenericRepo;
import com.cballestas.gestion_matriculas.service.RegistroMatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistroMatriculaServiceImpl extends CrudImpl<RegistroMatricula, String> implements RegistroMatriculaService {

    private final RegistroMatriculaRepository registroMatriculaRepository;

    @Override
    protected GenericRepo<RegistroMatricula, String> getRepo() {
        return registroMatriculaRepository;
    }
}
