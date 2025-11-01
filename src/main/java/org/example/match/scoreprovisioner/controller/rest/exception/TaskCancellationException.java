package org.example.match.scoreprovisioner.controller.rest.exception;

import java.util.UUID;

public class TaskCancellationException extends RuntimeException {

    private static final String ERROR_MSG = "[TASK, %s] Could not cancel scheduled task";

    public TaskCancellationException(UUID eventId) {
        super(ERROR_MSG.formatted(eventId));
    }
}
