package com.paulo.hurry_up.controller;

import com.paulo.hurry_up.dto.ResponseEventDTO;
import com.paulo.hurry_up.dto.RequestCreateEventDTO;
import com.paulo.hurry_up.dto.ResponseCreateEventDTO;
import com.paulo.hurry_up.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<ResponseCreateEventDTO> create(@RequestBody RequestCreateEventDTO dto) throws Exception {
        ResponseCreateEventDTO response = this.eventService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponseEventDTO>> findAll() {

        List<ResponseEventDTO> events = this.eventService.findAll();

        return ResponseEntity.ok(events);
    }
}
