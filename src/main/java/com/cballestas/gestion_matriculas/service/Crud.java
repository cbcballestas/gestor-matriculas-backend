package com.cballestas.gestion_matriculas.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Crud<T, ID> {
    Flux<T> findAll();

    Mono<T> findById(ID id);

    Mono<T> save(T t);

    Mono<T> update(ID id, T t);

    Mono<Boolean> deleteById(ID id);
}
