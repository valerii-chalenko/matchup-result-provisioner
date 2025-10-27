package org.example.match.scoreprovisioner.task;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.match.scoreprovisioner.client.http.ScoreClient;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class ScoreTask implements Runnable {

    @Setter
    private UUID eventId;
    private final TaskScheduler taskScheduler;
    private final ScoreClient scoreClient;
    private final KafkaTemplate<UUID, String> kafkaTemplate;

    @Override
    public void run() {

        try {
            val score = scoreClient.getScore(eventId);
            val currentScore = score.getCurrentScore();
            log.error("[HTTP, {}] Received core client response: {}", eventId, currentScore);
            kafkaTemplate.send("event.score", eventId, score.getCurrentScore());

        } catch (Exception exception) {
            log.error("[TASK, {}] Stopping score task", eventId, exception);
            taskScheduler.unScheduleScoreObservation(eventId);
        }
    }
}
