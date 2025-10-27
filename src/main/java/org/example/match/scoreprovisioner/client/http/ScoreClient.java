package org.example.match.scoreprovisioner.client.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.match.scoreprovisioner.client.http.model.ScoreResponseDto;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScoreClient {

    private final RestClient scoreServiceRestClient;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public ScoreResponseDto getScore(UUID id) {

        return scoreServiceRestClient
                .get()
                .uri("/api/v1/event/score?event_id=" + id)
                .retrieve()
                .body(ScoreResponseDto.class);
    }
}
