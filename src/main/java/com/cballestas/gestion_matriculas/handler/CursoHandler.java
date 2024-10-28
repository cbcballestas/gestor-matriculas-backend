package com.cballestas.gestion_matriculas.handler;

import com.cballestas.gestion_matriculas.dto.CursoDTO;
import com.cballestas.gestion_matriculas.exception.ModelNotFoundException;
import com.cballestas.gestion_matriculas.mapper.CursoMapper;
import com.cballestas.gestion_matriculas.service.CursoService;
import com.cballestas.gestion_matriculas.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
@RequiredArgsConstructor
public class CursoHandler {

    private final CursoService cursoService;
    private final CursoMapper cursoMapper;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> findAll(final ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cursoService.findAll().map(cursoMapper::toDTO), CursoDTO.class);
    }

    public Mono<ServerResponse> findById(final ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .flatMap(cursoService::findById)
                .map(cursoMapper::toDTO)
                .flatMap(e -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(Mono.error(new ModelNotFoundException(
                        String.format("Registro con ID %s NOT found",
                                request.pathVariable("id")), HttpStatus.BAD_REQUEST)));
    }

    public Mono<ServerResponse> save(final ServerRequest request) {
        return request.bodyToMono(CursoDTO.class)
                .flatMap(requestValidator::validate)
                .flatMap(dto -> cursoService.save(cursoMapper.toEntity(dto)))
                .map(cursoMapper::toDTO)
                .flatMap(e -> created(
                        URI.create(request.uri().toString().concat("/").concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                ).switchIfEmpty(Mono.error(new RuntimeException("El body de la petición NO debe estar vacío")));
    }

    public Mono<ServerResponse> update(final ServerRequest request) {
        String id = request.pathVariable("id");

        return request.bodyToMono(CursoDTO.class)
                .flatMap(requestValidator::validate)
                .map(dto -> {
                    dto.setId(id);
                    return dto;
                })
                .flatMap(e -> cursoService.update(id, cursoMapper.toEntity(e)))
                .map(cursoMapper::toDTO)
                .flatMap(e -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                ).switchIfEmpty(Mono.error(new ModelNotFoundException(
                        String.format("Registro con ID %s NOT found",
                                request.pathVariable("id")), HttpStatus.BAD_REQUEST)));
    }

    public Mono<ServerResponse> delete(final ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .flatMap(cursoService::deleteById)
                .flatMap(result -> {
                    if (Boolean.TRUE.equals(result)) {
                        return noContent().build();
                    } else {
                        return notFound().build();
                    }
                });
    }

}
