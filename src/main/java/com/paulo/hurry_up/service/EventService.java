package com.paulo.hurry_up.service;

import com.paulo.hurry_up.dto.RequestCreateEventDTO;
import com.paulo.hurry_up.dto.ResponseCreateEventDTO;
import com.paulo.hurry_up.model.Event;
import com.paulo.hurry_up.repository.EventRepository;
import org.springframework.stereotype.Service;

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
        event.setDate(dto.date());

        eventRepository.save(event);

        return new ResponseCreateEventDTO(event.getId());
    }

    public List<Event> findAll() {

        return eventRepository.findAll();
    }
}
