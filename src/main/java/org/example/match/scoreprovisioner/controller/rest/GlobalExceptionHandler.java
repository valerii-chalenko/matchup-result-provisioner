package org.example.match.scoreprovisioner.controller.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.example.match.scoreprovisioner.controller.rest.exception.RestError;
import org.example.match.scoreprovisioner.controller.rest.exception.TaskException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TaskException.class)
    public RestError handleMissingTask(
            HttpServletRequest request,
            TaskException exception
    ) {
        return RestError.builder()
                .message(exception.getMessage())
                .timestamp(Instant.now())
                .url(request.getRequestURI())
                .method(request.getMethod())
                .build();
    }
}
