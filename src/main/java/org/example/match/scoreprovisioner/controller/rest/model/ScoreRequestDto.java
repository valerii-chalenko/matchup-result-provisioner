package org.example.match.scoreprovisioner.controller.rest.model;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreRequestDto {
    @NotNull
    UUID id;
    @NotNull
    EventStatus status;
}
