package org.example.match.scoreprovisioner.controller.rest.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreRequestDto {
    UUID id;
    EventStatus status;
}
