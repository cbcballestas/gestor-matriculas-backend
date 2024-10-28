package com.cballestas.gestion_matriculas.controller;

import com.cballestas.gestion_matriculas.dto.CursoDTO;
import com.cballestas.gestion_matriculas.mapper.CursoMapper;
import com.cballestas.gestion_matriculas.service.CursoService;
import com.cballestas.gestion_matriculas.validator.RequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/cursos")
@RequiredArgsConstructor
public class CursoController {
    private final CursoService cursoService;
    private final CursoMapper cursoMapper;
    private final RequestValidator requestValidator;

    @GetMapping
    public Mono<ResponseEntity<Flux<CursoDTO>>> findAll() {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(this.cursoService.findAll().map(cursoMapper::toDTO))
        );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CursoDTO>> findById(@PathVariable String id) {
        return this.cursoService
                .findById(id)
                .map(cursoMapper::toDTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<CursoDTO>> save(@RequestBody CursoDTO cursoDTO, final ServerHttpRequest req) {
        return requestValidator.validate(cursoDTO)
                .flatMap(dto -> this.cursoService.save(cursoMapper.toEntity(dto)))
                .map(cursoMapper::toDTO)
                .map(e -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(e.getId())))
                        .body(e)
                );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CursoDTO>> update(@PathVariable String id, @RequestBody CursoDTO cursoDTO) {
        return Mono.just(cursoDTO)
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(requestValidator::validate)
                .flatMap(e -> this.cursoService.update(id, cursoMapper.toEntity(e)))
                .map(cursoMapper::toDTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return this.cursoService.deleteById(id)
                .flatMap(result -> {
                    if (result) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }
}
