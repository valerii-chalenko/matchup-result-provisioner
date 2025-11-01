package org.example.match.scoreprovisioner.controller.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.example.match.scoreprovisioner.controller.rest.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TaskException.class)
    public RestError handleTaskException(
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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateTaskException.class)
    public RestError handleDuplicateTask(
            HttpServletRequest request,
            RuntimeException exception
    ) {
        return RestError.builder()
                .message(exception.getMessage())
                .timestamp(Instant.now())
                .url(request.getRequestURI())
                .method(request.getMethod())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TaskNotFoundException.class)
    public RestError handleTaskNotFound(
            HttpServletRequest request,
            RuntimeException exception
    ) {
        return RestError.builder()
                .message(exception.getMessage())
                .timestamp(Instant.now())
                .url(request.getRequestURI())
                .method(request.getMethod())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TaskCancellationException.class)
    public RestError handleTaskCancellation(
            HttpServletRequest request,
            RuntimeException exception
    ) {
        return RestError.builder()
                .message(exception.getMessage())
                .timestamp(Instant.now())
                .url(request.getRequestURI())
                .method(request.getMethod())
                .build();
    }
}
