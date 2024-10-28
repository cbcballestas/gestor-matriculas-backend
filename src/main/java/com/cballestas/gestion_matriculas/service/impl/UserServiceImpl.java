package com.cballestas.gestion_matriculas.service.impl;

import com.cballestas.gestion_matriculas.model.Role;
import com.cballestas.gestion_matriculas.model.User;
import com.cballestas.gestion_matriculas.repo.GenericRepo;
import com.cballestas.gestion_matriculas.repo.RoleRepository;
import com.cballestas.gestion_matriculas.repo.UserRepository;
import com.cballestas.gestion_matriculas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CrudImpl<User, String> implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final BCryptPasswordEncoder bcrypt;

    @Override
    protected GenericRepo<User, String> getRepo() {
        return userRepo;
    }

    @Override
    public Mono<User> saveHash(User user) {
        user.setPassword(bcrypt.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Mono<com.cballestas.gestion_matriculas.security.User> searchByUser(String username) {
        return userRepo.findOneByUsername(username)
                .flatMap(user -> Flux.fromIterable(user.getRoles())
                        .flatMap(userRole -> roleRepo.findById(userRole.getId())
                                .map(Role::getName))
                        .collectList()
                        .map(roles -> new com.cballestas.gestion_matriculas.security.User(user.getUsername(), user.getPassword(), user.isStatus(), roles))
                );
    }

}
