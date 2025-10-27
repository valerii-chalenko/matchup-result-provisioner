package org.example.match.scoreprovisioner.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.match.scoreprovisioner.controller.rest.model.ScoreRequestDto;
import org.example.match.scoreprovisioner.task.TaskScheduler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreTaskService {

    private final TaskScheduler taskScheduler;

    public void observeEventScore(ScoreRequestDto requestDto) {

        val eventId = requestDto.getId();
        switch (requestDto.getStatus()) {
            case LIVE -> taskScheduler.scheduleScoreObservation(eventId);
            case NOT_LIVE -> taskScheduler.unScheduleScoreObservation(eventId);
        }
    }
}
