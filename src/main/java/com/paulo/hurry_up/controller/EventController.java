package com.paulo.hurry_up.controller;

import com.paulo.hurry_up.dto.*;
import com.paulo.hurry_up.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<ResponseCreateEventDTO> create(
            @RequestBody @Valid RequestCreateEventDTO dto
    ) throws Exception {
        ResponseCreateEventDTO response = this.eventService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseUpdateEventDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid RequestUpdateEventDTO dto
    ) {
        ResponseUpdateEventDTO response = this.eventService.update(id, dto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping
    public ResponseEntity<ResponseListAllEventDTO> findAll() {

        ResponseListAllEventDTO events = this.eventService.findAll();

        return ResponseEntity.ok(events);
    }

    @GetMapping("/paginate")
    public ResponseEntity<ResponseListAllEventDTO> findAllPaginated(
            @RequestParam String q,
            @RequestParam int page,
            @RequestParam int take
    ) {
        ResponseListAllEventDTO events = this.eventService.findAllPaginated(q, page, take);

        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEventDTO> findById(@PathVariable UUID id) throws Exception {
        ResponseEventDTO event = this.eventService.findById(id);

        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.eventService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/generate-upload-presigned-url")
    public ResponseEntity<ResponseGenerateUploadPresignedUrlDTO> generateUploadPresignedUrl(
            @RequestBody @Valid RequestGenerateUploadPresignedUrlDTO dto
    ) {
        ResponseGenerateUploadPresignedUrlDTO response = this.eventService.generateUploadPresignedUrl(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
