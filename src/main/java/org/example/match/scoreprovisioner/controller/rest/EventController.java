package org.example.match.scoreprovisioner.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.match.scoreprovisioner.controller.rest.model.ScoreRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EventController {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/event/status")
    public void eventStatus(@RequestBody ScoreRequestDto request) {

    }
}
