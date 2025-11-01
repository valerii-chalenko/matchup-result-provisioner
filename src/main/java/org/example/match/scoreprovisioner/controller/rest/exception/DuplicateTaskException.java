package org.example.match.scoreprovisioner.controller.rest.exception;

import java.util.UUID;

public class DuplicateTaskException extends RuntimeException {

    private static final String ERROR_MSG = "[TASK, %s] Event task already exists";

    public DuplicateTaskException(UUID eventId) {
        super(ERROR_MSG.formatted(eventId));
    }
}
