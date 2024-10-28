package com.cballestas.gestion_matriculas.service.impl;

import com.cballestas.gestion_matriculas.model.Role;
import com.cballestas.gestion_matriculas.repo.GenericRepo;
import com.cballestas.gestion_matriculas.repo.RoleRepository;
import com.cballestas.gestion_matriculas.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends CrudImpl<Role, String> implements RoleService {

    private final RoleRepository repo;

    @Override
    protected GenericRepo<Role, String> getRepo() {
        return repo;
    }


}
