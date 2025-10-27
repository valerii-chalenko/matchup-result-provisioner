package org.example.match.scoreprovisioner.task;

import lombok.val;
import org.example.match.scoreprovisioner.client.http.ScoreClient;
import org.example.match.scoreprovisioner.client.http.model.ScoreResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScoreTaskTest {

    UUID givenEventId = UUID.randomUUID();
    String givenScore = "0:0";

    @Mock
    TaskScheduler taskScheduler;
    @Mock
    ScoreClient scoreClient;
    @Mock
    KafkaTemplate<UUID, String> kafkaTemplate;

    @InjectMocks
    ScoreTask scoreTask;

    @BeforeEach
    void setUp() {

        scoreTask.setEventId(givenEventId);
    }

    @Test
    @DisplayName("When task is invoked - should call external API and send Kafka message")
    void successfulTaskInvocation() {

        val expectedScore = ScoreResponseDto.builder()
                .eventId(givenEventId)
                .currentScore(givenScore)
                .build();

        when(scoreClient.getScore(any())).thenReturn(expectedScore);

        CompletableFuture mockSendResult = CompletableFuture.completedFuture(mock(SendResult.class));
        when(kafkaTemplate.send(eq("event.score"), eq(givenEventId), eq(givenScore)))
                .thenReturn(mockSendResult);

        scoreTask.run();

        verify(scoreClient).getScore(givenEventId);
        verify(kafkaTemplate).send(eq("event.score"), eq(givenEventId), eq(givenScore));
        verify(taskScheduler, never()).unScheduleScoreObservation(any());
    }

    @Test
    @DisplayName("Should cancel score task on external API failure")
    void externalApiFailure() {

        when(scoreClient.getScore(any())).thenThrow(new IllegalArgumentException());

        scoreTask.run();

        verify(scoreClient).getScore(givenEventId);
        verify(kafkaTemplate, never()).send(eq("event.score"), eq(givenEventId), eq(givenScore));
        verify(taskScheduler).unScheduleScoreObservation(givenEventId);
    }
}