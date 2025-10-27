package org.example.match.scoreprovisioner.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.match.scoreprovisioner.controller.rest.exception.TaskException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskScheduler {

    private final static int TASK_RATE_SECONDS = 10;
    private final static int TASK_INITIAL_DELAY = 0;

    private final Map<UUID, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private final ScoreTaskFactory taskFactory;

    public void scheduleScoreObservation(UUID eventId) {

        val existingFuture = scheduledTasks.get(eventId);

        if (Objects.nonNull(existingFuture)) {
            throw new TaskException(eventId, "Task already exists");
        }

        ScheduledFuture<?> future = scheduledExecutorService.scheduleAtFixedRate(
                () -> Thread.startVirtualThread(taskFactory.createScoreTask(eventId)),
                TASK_INITIAL_DELAY,
                TASK_RATE_SECONDS,
                TimeUnit.SECONDS);

        scheduledTasks.put(eventId, future);
        log.info("[TASK, {}] Created event score task", eventId);
    }

    public void unScheduleScoreObservation(UUID eventId) {

        val existingFuture = scheduledTasks.get(eventId);

        if (Objects.isNull(existingFuture)) {
            throw new TaskException(eventId, "Task does not exist");
        }

        val future = scheduledTasks.get(eventId);

        if (Objects.isNull(future)) {
            throw new TaskException(eventId, "No scheduled future for event");
        }

        boolean cancelled = future.cancel(true);

        if (!cancelled) {
            throw new TaskException(eventId, "Could not delete event score task");
        }

        scheduledTasks.remove(eventId);
        log.info("[TASK, {}] Successfully stopped event score task", eventId);
    }
}
