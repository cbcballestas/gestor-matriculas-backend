package com.cballestas.gestion_matriculas.service.impl;

import com.cballestas.gestion_matriculas.model.Curso;
import com.cballestas.gestion_matriculas.repo.CursoRepository;
import com.cballestas.gestion_matriculas.repo.GenericRepo;
import com.cballestas.gestion_matriculas.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl extends CrudImpl<Curso, String> implements CursoService {

    private final CursoRepository cursoRepository;

    @Override
    protected GenericRepo<Curso, String> getRepo() {
        return cursoRepository;
    }
}
