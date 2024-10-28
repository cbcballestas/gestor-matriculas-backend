package com.cballestas.gestion_matriculas.config;

import com.cballestas.gestion_matriculas.handler.CursoHandler;
import com.cballestas.gestion_matriculas.handler.EstudianteHandler;
import com.cballestas.gestion_matriculas.handler.RegistroMatriculaHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Value("${routing.cursos-path}")
    private String cursosPath;

    @Value("${routing.estudiantes-path}")
    private String estudiantesPath;

    @Value("${routing.registro-path}")
    private String registroPath;

    @Bean
    public RouterFunction<ServerResponse> cursosRouting(CursoHandler cursoHandler) {
        return route().path(cursosPath, builder -> builder
                .GET("", cursoHandler::findAll)
                .GET("/{id}", cursoHandler::findById)
                .POST("", cursoHandler::save)
                .PUT("/{id}", cursoHandler::update)
                .DELETE("/{id}", cursoHandler::delete)
        ).build();
    }

    @Bean
    public RouterFunction<ServerResponse> estudiantesRouting(EstudianteHandler estudianteHandler) {
        return RouterFunctions.route().path(estudiantesPath, builder -> builder
                .GET("", estudianteHandler::findAll)
                .GET("/{id}", estudianteHandler::findById)
                .POST("", estudianteHandler::save)
                .PUT("/{id}", estudianteHandler::update)
                .DELETE("/{id}", estudianteHandler::delete)
        ).build();
    }

    @Bean
    public RouterFunction<ServerResponse> registroMatriculaRouting(RegistroMatriculaHandler registroMatriculaHandler) {
        return route().path(registroPath, builder -> builder
                .GET("", registroMatriculaHandler::findAll)
                .GET("/{id}", registroMatriculaHandler::findById)
                .POST("", registroMatriculaHandler::save)
        ).build();
    }
}
