package org.example.match.scoreprovisioner.task;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.match.scoreprovisioner.client.http.ScoreClient;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class ScoreTask implements Runnable {

    @Setter
    private UUID eventId;
    private final ScoreClient scoreClient;
    private final KafkaTemplate<UUID, String> kafkaTemplate;
    private final Cache scoresCache;

    public ScoreTask(
            ScoreClient scoreClient,
            KafkaTemplate<UUID, String> kafkaTemplate,
            CacheManager cacheManager
    ) {
        this.scoreClient = scoreClient;
        this.kafkaTemplate = kafkaTemplate;
        this.scoresCache = cacheManager.getCache("scores");
    }

    @Override
    public void run() {
        try {
            val score = scoreClient.getScore(eventId);
            val currentScore = score.getCurrentScore();

            String previousScore = scoresCache.get(eventId, String.class);

            if (previousScore == null || !previousScore.equals(currentScore)) {
                log.info("[HTTP, {}] Received score client response (changed): {}", eventId, currentScore);
                kafkaTemplate
                        .send("event.score", eventId, currentScore)
                        .whenComplete((res, ex) -> {
                            if (Objects.isNull(ex)) {
                                val sentResult = res.getProducerRecord().value();
                                scoresCache.put(eventId, sentResult);
                            } else {
                                log.warn("[KAFKA, {}] Send failed, will not update cache: {}", eventId, ex.toString());
                            }
                        });
            } else {
                log.info("[TASK, {}] Score is same, skipping kafka publish: {}", eventId, currentScore);
            }

        } catch (Exception exception) {
            log.warn("[TASK, {}] Error during score fetch/publish: {}", eventId, exception.toString());
        }
    }
}
