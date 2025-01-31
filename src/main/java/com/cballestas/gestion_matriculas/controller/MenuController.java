package com.cballestas.gestion_matriculas.controller;

import com.cballestas.gestion_matriculas.model.Menu;
import com.cballestas.gestion_matriculas.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<Menu>>> getMenus(){
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getAuthorities)
                .map(roles -> {
                    String roleStrings = roles.stream().map(Objects::toString).collect(Collectors.joining(","));
                    String[] stringArray = roleStrings.split(",");
                    return service.getMenus(stringArray);
                })
                .flatMap(fx -> Mono.just(ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fx)
                ));
    }

}
