package org.example.match.scoreprovisioner.controller.rest.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestError {

    Instant timestamp;
    String method;
    String url;
    String message;
}
