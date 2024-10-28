package com.cballestas.gestion_matriculas.service;

import com.cballestas.gestion_matriculas.model.Estudiante;
import reactor.core.publisher.Flux;

public interface EstudianteService extends Crud<Estudiante, String> {
    Flux<Estudiante> findAllOrderByEdadBySort(String orderBy, String sortDirection);
}
