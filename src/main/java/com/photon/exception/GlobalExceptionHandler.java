package com.photon.exception;

import com.photon.constant.AppConstant;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Mono<ProblemDetail> handleRuntime(RuntimeException ex) {
        final var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle(AppConstant.RESOURCE_NOT_FOUND);
        problem.setType(URI.create(""));
        return Mono.just(problem);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ProblemDetail> handleValidation(ServerWebInputException ex) {
        final var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, AppConstant.VALIDATION_FAILED);
        problem.setTitle(AppConstant.INVALID_INPUT);
        problem.setDetail(ex.getReason());
        problem.setType(URI.create(""));
        return Mono.just(problem);
    }

    @ExceptionHandler(ErrorResponseException.class)
    public Mono<ProblemDetail> handleOtherErrors(ErrorResponseException ex) {
        return Mono.just(ex.getBody());
    }
}
