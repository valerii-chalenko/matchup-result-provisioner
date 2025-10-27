package org.example.match.scoreprovisioner.client.http.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreResponseDto {
    UUID eventId;
    String currentScore;
}
