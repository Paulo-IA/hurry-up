package com.paulo.hurry_up.service;

import com.paulo.hurry_up.dto.*;
import com.paulo.hurry_up.domain.Event;
import com.paulo.hurry_up.exceptions.EventNotFoundException;
import com.paulo.hurry_up.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public ResponseCreateEventDTO create(RequestCreateEventDTO dto) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());

        ZonedDateTime eventDate = dto.getDate().withZoneSameInstant(ZoneId.of("UTC"));
        event.setDate(eventDate);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        event.setCreatedAt(now);

        eventRepository.save(event);

        return new ResponseCreateEventDTO(event.getId());
    }

    public ResponseUpdateEventDTO update(UUID id, RequestUpdateEventDTO dto) throws EventNotFoundException {
        Event event = this.eventRepository.findById(id).orElseThrow(EventNotFoundException::new);

        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setDate(dto.getDate());

        this.eventRepository.save(event);

        return new ResponseUpdateEventDTO(event.getId());
    }

    public ResponseListAllEventDTO findAll() {
        List<Event> events = eventRepository.findAll();


        List<ResponseEventDTO> eventsResponse = events.stream().map(e -> {
            Countdown countdown = new Countdown(e.getDate());

            return new ResponseEventDTO(
                    e.getId(),
                    e.getName(),
                    e.getDescription(),
                    e.getDate(),
                    e.getCreatedAt(),
                    countdown);
        }).toList();

        return new ResponseListAllEventDTO(eventsResponse);
    }

    public ResponseListAllEventDTO findAllPaginated(String q, int page, int take) {
        Pageable pageable = PageRequest.of(page, take);

        Page<Event> eventsPage = this.eventRepository.findAllByEventName(q, pageable);

        List<ResponseEventDTO> events = eventsPage.map(e -> {
            Countdown countdown = new Countdown(e.getDate());

            return new ResponseEventDTO(e.getId(),
                    e.getName(),
                    e.getDescription(),
                    e.getDate(),
                    e.getCreatedAt(),
                    countdown);
        }).toList();

        return new ResponseListAllEventDTO(events);
    }

    public ResponseEventDTO findById(UUID id) throws EventNotFoundException {
        Event event = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);

        Countdown countdown = new Countdown(event.getDate());

        return new ResponseEventDTO(event.getId(), event.getName(), event.getDescription(), event.getDate(), event.getCreatedAt(), countdown);
    }

    public void delete(UUID id) throws EventNotFoundException {
        Event event = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);

        eventRepository.deleteById(event.getId());
    }
}
