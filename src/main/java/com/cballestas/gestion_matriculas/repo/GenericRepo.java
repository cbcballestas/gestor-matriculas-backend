package com.cballestas.gestion_matriculas.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepo<T, ID> extends ReactiveMongoRepository<T, ID> {
}
