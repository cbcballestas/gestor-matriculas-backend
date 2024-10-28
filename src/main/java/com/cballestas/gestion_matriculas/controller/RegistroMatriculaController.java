package com.cballestas.gestion_matriculas.controller;

import com.cballestas.gestion_matriculas.dto.RegistroMatriculaDTO;
import com.cballestas.gestion_matriculas.mapper.RegistroMatriculaMapper;
import com.cballestas.gestion_matriculas.model.RegistroMatricula;
import com.cballestas.gestion_matriculas.service.RegistroMatriculaService;
import com.cballestas.gestion_matriculas.validator.RequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/registro-matricula")
@RequiredArgsConstructor
public class RegistroMatriculaController {
    private final RegistroMatriculaService registroMatriculaService;
    private final RegistroMatriculaMapper registroMatriculaMapper;
    private final RequestValidator requestValidator;

    @GetMapping
    public Mono<ResponseEntity<Flux<RegistroMatriculaDTO>>> findAll() {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(this.registroMatriculaService.findAll().map(registroMatriculaMapper::toDTO))
        );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<RegistroMatriculaDTO>> findById(@PathVariable String id) {
        return this.registroMatriculaService
                .findById(id)
                .map(registroMatriculaMapper::toDTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<RegistroMatriculaDTO>> save(@RequestBody RegistroMatriculaDTO registroMatriculaDTO, final ServerHttpRequest req) {
        return requestValidator.validate(registroMatriculaDTO)
                .flatMap(dto -> this.registroMatriculaService.save(registroMatriculaMapper.toEntity(dto)))
                .map(registroMatriculaMapper::toDTO)
                .map(e -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(e.getId())))
                        .body(e)
                );
    }
}
