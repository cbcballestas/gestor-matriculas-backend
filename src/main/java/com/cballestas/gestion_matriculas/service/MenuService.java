package com.cballestas.gestion_matriculas.service;

import com.cballestas.gestion_matriculas.model.Menu;
import reactor.core.publisher.Flux;

public interface MenuService extends Crud<Menu, String>{

    Flux<Menu> getMenus(String[] roles);
}
