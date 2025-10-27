package org.example.match.scoreprovisioner.task;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ScoreTaskFactory {

    private final ApplicationContext applicationContext;

    public Runnable createScoreTask(UUID eventId) {
        ScoreTask task = applicationContext.getBean(ScoreTask.class);
        task.setEventId(eventId);
        return task;
    }
}
