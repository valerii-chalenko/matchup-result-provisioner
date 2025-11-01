package org.example.match.scoreprovisioner.controller.rest.exception;

import java.util.UUID;

public class TaskException extends RuntimeException {

    private final static String ERROR_MSG = "[TASK, %s] Event task failure. %s";

    public TaskException(UUID eventId, String detailedMessage) {
        super(ERROR_MSG.formatted(eventId, detailedMessage));
    }
}
