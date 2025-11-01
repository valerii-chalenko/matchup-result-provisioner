package org.example.match.scoreprovisioner.task;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.match.scoreprovisioner.controller.rest.exception.DuplicateTaskException;
import org.example.match.scoreprovisioner.controller.rest.exception.TaskCancellationException;
import org.example.match.scoreprovisioner.controller.rest.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskScheduler {

    private final Map<UUID, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduledExecutorService;
    private final ScoreTaskFactory taskFactory;

    @Value("${task.scheduler.rate-seconds:2}")
    private int rateSeconds;

    @Value("${task.scheduler.initial-delay-seconds:0}")
    private int initialDelaySeconds;

    public void scheduleScoreObservation(UUID eventId) {
        val existing = scheduledTasks.get(eventId);
        if (Objects.nonNull(existing)) {
            throw new DuplicateTaskException(eventId);
        }

        ScheduledFuture<?> future = scheduledExecutorService.scheduleAtFixedRate(
                taskFactory.createScoreTask(eventId),
                initialDelaySeconds,
                rateSeconds,
                TimeUnit.SECONDS);

        if (Objects.nonNull(scheduledTasks.putIfAbsent(eventId, future))) {
            future.cancel(true);
            throw new DuplicateTaskException(eventId);
        }
        log.info("[TASK, {}] Created event score task (rate={}s, initialDelay={}s)", eventId, rateSeconds, initialDelaySeconds);
    }

    public void unScheduleScoreObservation(UUID eventId) {
        val future = scheduledTasks.remove(eventId);
        if (Objects.isNull(future)) {
            throw new TaskNotFoundException(eventId);
        }
        boolean cancelled = future.cancel(true);
        if (!cancelled) {
            throw new TaskCancellationException(eventId);
        }
        log.info("[TASK, {}] Successfully stopped event score task", eventId);
    }

    @PreDestroy
    void shutdown() {
        log.info("[TASK] Shutting down scheduler");
        scheduledExecutorService.shutdown();
        try {
            if (!scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS)) {
                log.warn("[TASK] Forcing scheduler shutdown");
                scheduledExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            scheduledExecutorService.shutdownNow();
        }
    }
}
