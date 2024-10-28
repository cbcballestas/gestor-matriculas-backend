package com.cballestas.gestion_matriculas.repo;

import com.cballestas.gestion_matriculas.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends GenericRepo<User, String> {

    //@Query("{username: ?1}")
    Mono<User> findOneByUsername(String username);
}
