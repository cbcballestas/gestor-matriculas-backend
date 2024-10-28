package com.cballestas.gestion_matriculas.handler;

import com.cballestas.gestion_matriculas.dto.EstudianteDTO;
import com.cballestas.gestion_matriculas.exception.ModelNotFoundException;
import com.cballestas.gestion_matriculas.mapper.EstudianteMapper;
import com.cballestas.gestion_matriculas.service.EstudianteService;
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
public class EstudianteHandler {

    private final EstudianteService estudianteService;
    private final EstudianteMapper estudianteMapper;
    private final RequestValidator requestValidator;

    /**
     * Método que se encarga de obtener el listado de estudiantes,
     * ordenado por edad de forma ascendente ( por defecto)
     * <p>
     * orderBy ( campo por el que se desea realizar el ordenamiento)
     * sortDirection ( dirección de ordenamiento, ascendente/descendente)
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> findAll(final ServerRequest request) {
        String orderBy = request.queryParam("orderBy").orElse("edad");
        String sortDirection = request.queryParam("direction").orElse("ASC");

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(estudianteService.findAllOrderByEdadBySort(orderBy, sortDirection).map(estudianteMapper::toDTO), EstudianteDTO.class);
    }

    public Mono<ServerResponse> findById(final ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .flatMap(estudianteService::findById)
                .map(estudianteMapper::toDTO)
                .flatMap(e -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(Mono.error(new ModelNotFoundException(
                        String.format("Registro con ID %s NOT found",
                                request.pathVariable("id")), HttpStatus.BAD_REQUEST)));
    }

    public Mono<ServerResponse> save(final ServerRequest request) {
        return request.bodyToMono(EstudianteDTO.class)
                .flatMap(requestValidator::validate)
                .flatMap(dto -> estudianteService.save(estudianteMapper.toEntity(dto)))
                .map(estudianteMapper::toDTO)
                .flatMap(e -> created(
                        URI.create(request.uri().toString().concat("/").concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                ).switchIfEmpty(Mono.error(new RuntimeException("El body de la petición NO debe estar vacío")));
    }

    public Mono<ServerResponse> update(final ServerRequest request) {
        String id = request.pathVariable("id");

        return request.bodyToMono(EstudianteDTO.class)
                .flatMap(requestValidator::validate)
                .map(dto -> {
                    dto.setId(id);
                    return dto;
                })
                .flatMap(e -> estudianteService.update(id, estudianteMapper.toEntity(e)))
                .map(estudianteMapper::toDTO)
                .flatMap(e -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                ).switchIfEmpty(Mono.error(new ModelNotFoundException(
                        String.format("Registro con ID %s NOT found",
                                request.pathVariable("id")), HttpStatus.BAD_REQUEST)));
    }

    public Mono<ServerResponse> delete(final ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .flatMap(estudianteService::deleteById)
                .flatMap(result -> {
                    if (Boolean.TRUE.equals(result)) {
                        return noContent().build();
                    } else {
                        return notFound().build();
                    }
                });
    }

}
