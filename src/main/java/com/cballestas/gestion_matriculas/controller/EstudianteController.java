package com.cballestas.gestion_matriculas.controller;

import com.cballestas.gestion_matriculas.dto.EstudianteDTO;
import com.cballestas.gestion_matriculas.mapper.EstudianteMapper;
import com.cballestas.gestion_matriculas.service.EstudianteService;
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
@RequestMapping("/api/v1/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {
    private final EstudianteService estudianteService;
    private final EstudianteMapper estudianteMapper;
    private final RequestValidator requestValidator;

    @GetMapping
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> findAll(
            @RequestParam(defaultValue = "edad", required = false) String orderBy,
            @RequestParam(defaultValue = "asc", required = false) String direction
    ) {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(this.estudianteService.findAllOrderByEdadBySort(orderBy, direction).map(estudianteMapper::toDTO))
        );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> findById(@PathVariable String id) {
        return this.estudianteService
                .findById(id)
                .map(estudianteMapper::toDTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<EstudianteDTO>> save(@RequestBody EstudianteDTO estudianteDTO, final ServerHttpRequest req) {
        return requestValidator.validate(estudianteDTO)
                .flatMap(e -> this.estudianteService.save(estudianteMapper.toEntity(e)))
                .map(estudianteMapper::toDTO)
                .map(e -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(e.getId())))
                        .body(e)
                );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> update(@PathVariable String id, @RequestBody EstudianteDTO estudianteDTO) {
        return Mono.just(estudianteDTO)
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(requestValidator::validate)
                .flatMap(e -> this.estudianteService.update(id, estudianteMapper.toEntity(e)))
                .map(estudianteMapper::toDTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return this.estudianteService.deleteById(id)
                .flatMap(result -> {
                    if (Boolean.TRUE.equals(result)) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }
}
