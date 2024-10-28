package com.cballestas.gestion_matriculas.repo;

import com.cballestas.gestion_matriculas.model.Menu;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface MenuRepository extends GenericRepo<Menu, String> {

    @Query("{ 'roles' : { $in:  ?0} }")
    Flux<Menu> getMenus(String[] roles);

}
