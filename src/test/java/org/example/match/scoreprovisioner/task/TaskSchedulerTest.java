package org.example.match.scoreprovisioner.task;

import org.example.match.scoreprovisioner.controller.rest.exception.DuplicateTaskException;
import org.example.match.scoreprovisioner.controller.rest.exception.TaskNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskSchedulerTest {

    UUID givenEventId = UUID.randomUUID();

    @Mock
    ScheduledExecutorService scheduledExecutorService;

    @SuppressWarnings("unused")
    @Mock
    ScoreTaskFactory factory;

    @InjectMocks
    TaskScheduler taskScheduler;

    @Test
    @DisplayName("Should add task to schedule successfully")
    void shouldScheduleScoreObservation() {
        @SuppressWarnings("unchecked")
        ScheduledFuture<Object> future = mock(ScheduledFuture.class);
        when(scheduledExecutorService.scheduleAtFixedRate(any(Runnable.class), anyLong(), anyLong(), any()))
                .thenReturn((ScheduledFuture) future);
        when(future.cancel(true)).thenReturn(true);
        taskScheduler.scheduleScoreObservation(givenEventId);
    }

    @Test
    @DisplayName("Should throw if score task with same id is already present")
    void shouldFailOnDuplicatedTask() {
        @SuppressWarnings("unchecked")
        ScheduledFuture<Object> future = mock(ScheduledFuture.class);
        when(scheduledExecutorService.scheduleAtFixedRate(any(Runnable.class), anyLong(), anyLong(), any()))
                .thenReturn((ScheduledFuture) future);
        when(future.cancel(true)).thenReturn(true);
        taskScheduler.scheduleScoreObservation(givenEventId);
        assertThrows(DuplicateTaskException.class, () -> taskScheduler.scheduleScoreObservation(givenEventId));
    }

    @Test
    @DisplayName("Should throw if score task with same id is already present")
    void shouldUnscheduleScoreObservation() {
        @SuppressWarnings("unchecked")
        ScheduledFuture<Object> future = mock(ScheduledFuture.class);
        when(scheduledExecutorService.scheduleAtFixedRate(any(Runnable.class), anyLong(), anyLong(), any()))
                .thenReturn((ScheduledFuture) future);
        when(future.cancel(true)).thenReturn(true);
        taskScheduler.scheduleScoreObservation(givenEventId);
        taskScheduler.unScheduleScoreObservation(givenEventId);
    }

    @Test
    @DisplayName("Should throw if score task with required id is not present")
    void shouldFailIfNoTaskOnUnschedule() {

        assertThrows(TaskNotFoundException.class, () -> taskScheduler.unScheduleScoreObservation(givenEventId));
    }
}