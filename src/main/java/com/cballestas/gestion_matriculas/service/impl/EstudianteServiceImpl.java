package com.cballestas.gestion_matriculas.service.impl;

import com.cballestas.gestion_matriculas.model.Estudiante;
import com.cballestas.gestion_matriculas.repo.EstudianteRepository;
import com.cballestas.gestion_matriculas.repo.GenericRepo;
import com.cballestas.gestion_matriculas.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl extends CrudImpl<Estudiante, String> implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    protected GenericRepo<Estudiante, String> getRepo() {
        return estudianteRepository;
    }

    /**
     * Método que se encarga de obtener los estudiantes ordenados por
     * el campo deseado ( ya sea de forma ascendente ó descendente)
     *
     * @param sortDirection tipo de ordenamiento
     * @return
     */
    @Override
    public Flux<Estudiante> findAllOrderByEdadBySort(String orderBy, String sortDirection) {
        Sort sort = Sort.by(
                sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.Direction.DESC : Sort.Direction.ASC,
                orderBy
        );
        Query query = new Query();
        query.with(sort);
        return mongoTemplate.find(query, Estudiante.class);
    }
}
