package com.cballestas.gestion_matriculas.service.impl;

import com.cballestas.gestion_matriculas.repo.GenericRepo;
import com.cballestas.gestion_matriculas.service.Crud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CrudImpl<T, ID> implements Crud<T, ID> {

    protected abstract GenericRepo<T, ID> getRepo();

    @Override
    public Flux<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public Mono<T> findById(ID id) {
        return getRepo().findById(id);
    }

    @Override
    public Mono<T> save(T t) {
        return getRepo().save(t);
    }

    @Override
    public Mono<T> update(ID id, T t) {
        return getRepo().findById(id).flatMap(e -> getRepo().save(t));
    }

    @Override
    public Mono<Boolean> deleteById(ID id) {
        return getRepo().findById(id)
                .hasElement()
                .flatMap(result -> {
                    if (result) {
                        return getRepo().deleteById(id).thenReturn(Boolean.TRUE);
                    } else {
                        return Mono.just(false);
                    }
                });
    }
}
