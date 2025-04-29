package com.paulo.hurry_up.service;

import com.paulo.hurry_up.dto.ResponseEventDTO;
import com.paulo.hurry_up.dto.RequestCreateEventDTO;
import com.paulo.hurry_up.dto.ResponseCreateEventDTO;
import com.paulo.hurry_up.model.Event;
import com.paulo.hurry_up.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public ResponseCreateEventDTO create(RequestCreateEventDTO dto) throws Exception {
        if (dto.name() == null) {
            throw new IllegalArgumentException("Name is required");
        }

        if (dto.date() == null) {
            throw new IllegalArgumentException("Date is required");
        }

        Event event = new Event();
        event.setName(dto.name());
        event.setDescription(dto.description());

        ZonedDateTime eventDate = dto.date().withZoneSameInstant(ZoneId.of("UTC"));
        event.setDate(eventDate);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        event.setCreatedAt(now);

        eventRepository.save(event);

        return new ResponseCreateEventDTO(event.getId());
    }

    public List<ResponseEventDTO> findAll() {
        List<Event> events = eventRepository.findAll();


        return events.stream().map(e -> {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
            Long daysToGo = Duration.between(now, e.getDate()).toDays();

            return new ResponseEventDTO(
                    e.getId(),
                    e.getName(),
                    e.getDescription(),
                    e.getDate(),
                    e.getCreatedAt(),
                    daysToGo);
        }).toList();
    }
}
