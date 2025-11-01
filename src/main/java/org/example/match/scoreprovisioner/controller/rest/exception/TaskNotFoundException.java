package org.example.match.scoreprovisioner.controller.rest.exception;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {

    private static final String ERROR_MSG = "[TASK, %s] Event task does not exist";

    public TaskNotFoundException(UUID eventId) {
        super(ERROR_MSG.formatted(eventId));
    }
}
