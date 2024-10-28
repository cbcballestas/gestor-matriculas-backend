package com.cballestas.gestion_matriculas.handler;

import com.cballestas.gestion_matriculas.dto.RegistroMatriculaDTO;
import com.cballestas.gestion_matriculas.exception.ModelNotFoundException;
import com.cballestas.gestion_matriculas.mapper.RegistroMatriculaMapper;
import com.cballestas.gestion_matriculas.service.RegistroMatriculaService;
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
public class RegistroMatriculaHandler {

    private final RegistroMatriculaService registroMatriculaService;
    private final RegistroMatriculaMapper registroMatriculaMapper;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> findAll(final ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(registroMatriculaService.findAll().map(registroMatriculaMapper::toDTO), RegistroMatriculaDTO.class);
    }

    public Mono<ServerResponse> findById(final ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .flatMap(registroMatriculaService::findById)
                .map(registroMatriculaMapper::toDTO)
                .flatMap(e -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(Mono.error(new ModelNotFoundException(
                        String.format("Registro con ID %s NOT found",
                                request.pathVariable("id")), HttpStatus.BAD_REQUEST)));
    }

    public Mono<ServerResponse> save(final ServerRequest request) {
        return request.bodyToMono(RegistroMatriculaDTO.class)
                .flatMap(requestValidator::validate)
                .flatMap(dto -> registroMatriculaService.save(registroMatriculaMapper.toEntity(dto)))
                .map(registroMatriculaMapper::toDTO)
                .flatMap(e -> created(
                        URI.create(request.uri().toString().concat("/").concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                ).switchIfEmpty(Mono.error(new RuntimeException("El body de la petición NO debe estar vacío")));
    }

}
