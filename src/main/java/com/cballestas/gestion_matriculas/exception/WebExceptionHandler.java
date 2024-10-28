package com.cballestas.gestion_matriculas.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public WebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(configurer.getWriters());
        this.setMessageReaders(configurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String, Object> generalError = getErrorAttributes(request, ErrorAttributeOptions.defaults());

        BaseErrorResponse errorResponse = this.handleErrorResponse(generalError, request);

        return ServerResponse.status(HttpStatus.valueOf(errorResponse.getStatusCode()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorResponse));
    }

    /**
     * Método que se encarga manejar los errores del request
     *
     * @param generalError Errores del request
     * @param request      Server Request
     * @return
     */
    private BaseErrorResponse handleErrorResponse(Map<String, Object> generalError, ServerRequest request) {

        // Se obtienen datos del request
        int statusCode = this.getStatusCode(generalError);
        HttpStatus status = Optional.of(HttpStatus.valueOf(statusCode)).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
        Throwable error = getError(request);

        if (error instanceof ValidationErrorException validationErrorException) {
            return ErrorsResponse.builder()
                    .statusCode(status.value())
                    .errors(validationErrorException.getErrors())
                    .build();
        }

        return ErrorResponse.builder()
                .statusCode(status.value())
                .message(error.getMessage())
                .build();
    }

    /**
     * Método que se encarga de armar los atributos del response ( para errores )
     *
     * @param request
     * @param options
     * @return
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        Throwable error = getError(request);

        if (error instanceof ValidationErrorException validationErrorException) {
            errorAttributes.clear();
            errorAttributes.put("status", validationErrorException.getStatus());
            errorAttributes.put("errors", validationErrorException.getErrors());
        } else if (error instanceof ModelNotFoundException modelNotFoundException) {
            errorAttributes.clear();
            errorAttributes.put("status", modelNotFoundException.getStatus());
            errorAttributes.put("error", modelNotFoundException.getMessage());
        }
        return errorAttributes;
    }

    private int getStatusCode(Map<String, Object> generalError) {
        if (generalError.get("status") instanceof HttpStatus status) return status.value();
        return (int) generalError.get("status");
    }
}
