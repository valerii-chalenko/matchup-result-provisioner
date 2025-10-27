package org.example.match.scoreprovisioner.task;

import org.example.match.scoreprovisioner.controller.rest.exception.TaskException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskSchedulerTest {

    UUID givenEventId = UUID.randomUUID();

    @Mock
    ScheduledExecutorService scheduledExecutorService;

    @Mock
    ScoreTaskFactory factory;

    @InjectMocks
    TaskScheduler taskScheduler;

    @Test
    @DisplayName("Should add task to schedule successfully")
    void shouldScheduleScoreObservation() {

        taskScheduler.scheduleScoreObservation(givenEventId);
    }

    @Test
    @DisplayName("Should throw if score task with same id is already present")
    void shouldFailOnDuplicatedTask() {

        taskScheduler.scheduleScoreObservation(givenEventId);
        assertThrows(TaskException.class, () -> taskScheduler.scheduleScoreObservation(givenEventId));
    }

    @Test
    @DisplayName("Should throw if score task with same id is already present")
    void shouldUnscheduleScoreObservation() {

        taskScheduler.scheduleScoreObservation(givenEventId);
        taskScheduler.unScheduleScoreObservation(givenEventId);
    }

    @Test
    @DisplayName("Should throw if score task with required id is not present")
    void shouldFailIfNoTaskOnUnschedule() {

        assertThrows(TaskException.class, () -> taskScheduler.unScheduleScoreObservation(givenEventId));
    }
}