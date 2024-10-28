package com.cballestas.gestion_matriculas.service;

import com.cballestas.gestion_matriculas.model.User;
import reactor.core.publisher.Mono;

public interface UserService extends Crud<User, String>{

    Mono<User> saveHash(User user);
    Mono<com.cballestas.gestion_matriculas.security.User> searchByUser(String username);
}
