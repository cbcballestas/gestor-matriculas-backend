package com.cballestas.gestion_matriculas.service.impl;

import com.cballestas.gestion_matriculas.model.Menu;
import com.cballestas.gestion_matriculas.repo.GenericRepo;
import com.cballestas.gestion_matriculas.repo.MenuRepository;
import com.cballestas.gestion_matriculas.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends CrudImpl<Menu, String> implements MenuService {

    private final MenuRepository repo;

    @Override
    protected GenericRepo<Menu, String> getRepo() {
        return repo;
    }


    @Override
    public Flux<Menu> getMenus(String[] roles) {
        return repo.getMenus(roles);
    }
}
