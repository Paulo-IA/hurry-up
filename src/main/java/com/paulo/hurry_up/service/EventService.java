package com.paulo.hurry_up.service;

import com.paulo.hurry_up.dto.*;
import com.paulo.hurry_up.domain.Event;
import com.paulo.hurry_up.exceptions.EventNotFoundException;
import com.paulo.hurry_up.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    private static final Long DAYS_IN_THE_WEEK = 7L;

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public ResponseCreateEventDTO create(RequestCreateEventDTO dto) {
        if (dto.getName() == null) {
            throw new IllegalArgumentException("Name is required");
        }

        if (dto.getDate() == null) {
            throw new IllegalArgumentException("Date is required");
        }

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

    public List<ResponseEventDTO> findAll() {
        List<Event> events = eventRepository.findAll();


        return events.stream().map(e -> {
            Countdown countdown = new Countdown();
            countdown.setTotalDays(daysFromNow(e.getDate()));
            countdown.setWorkingDays(workingDaysFromNow(e.getDate()));

            return new ResponseEventDTO(
                    e.getId(),
                    e.getName(),
                    e.getDescription(),
                    e.getDate(),
                    e.getCreatedAt(),
                    countdown);
        }).toList();
    }

    public List<ResponseEventDTO> findAllPaginated(String q, int page, int take) {
        Pageable pageable = PageRequest.of(page, take);

        Page<Event> eventsPage = this.eventRepository.findAllByEventName(q, pageable);

        return eventsPage.map(e -> {
            Countdown countdown = new Countdown();
            countdown.setTotalDays(daysFromNow(e.getDate()));
            countdown.setWorkingDays(workingDaysFromNow(e.getDate()));

            return new ResponseEventDTO(e.getId(),
                    e.getName(),
                    e.getDescription(),
                    e.getDate(),
                    e.getCreatedAt(),
                    countdown);
        }).toList();
    }

    public ResponseEventDTO findById(UUID id) throws EventNotFoundException {
        Event event = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);

        Countdown countdown = new Countdown();
        countdown.setTotalDays(daysFromNow(event.getDate()));
        countdown.setWorkingDays(workingDaysFromNow(event.getDate()));

        return new ResponseEventDTO(event.getId(), event.getName(), event.getDescription(), event.getDate(), event.getCreatedAt(), countdown);
    }

    public void delete(UUID id) throws EventNotFoundException {
        Event event = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);

        eventRepository.deleteById(event.getId());
    }

    private Long daysFromNow(ZonedDateTime date) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));

        return Duration.between(now, date).toDays();
    }

    private Integer workingDaysFromNow(ZonedDateTime date) {
        Long daysFromNow = daysFromNow(date);

        int weekendsDays = Math.round((float) daysFromNow / DAYS_IN_THE_WEEK) * 2;

        return daysFromNow.intValue() - weekendsDays;
    }
}
