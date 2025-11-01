package org.example.match.scoreprovisioner.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.match.scoreprovisioner.controller.rest.model.ScoreRequestDto;
import org.example.match.scoreprovisioner.service.ScoreTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EventController {

    private final ScoreTaskService scoreTaskService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/event/status")
    public void eventStatus(@RequestBody @Valid ScoreRequestDto request) {
        scoreTaskService.observeEventScore(request);
    }
}
