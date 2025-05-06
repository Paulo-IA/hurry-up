package com.paulo.hurry_up.service;

import com.paulo.hurry_up.dto.*;
import com.paulo.hurry_up.domain.Event;
import com.paulo.hurry_up.exceptions.InvalidEventFileExtensionException;
import com.paulo.hurry_up.exceptions.EventNotFoundException;
import com.paulo.hurry_up.repository.EventRepository;
import com.paulo.hurry_up.service.provider.PresignedUrlProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EventService {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    private final EventRepository eventRepository;
    private final PresignedUrlProvider presignedUrlProvider;

    public EventService(
            EventRepository eventRepository,
            PresignedUrlProvider presignedUrlProvider
    ) {
        this.eventRepository = eventRepository;
        this.presignedUrlProvider = presignedUrlProvider;
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

    public void updateEventImage(UUID id, RequestUpdateEventImageDTO dto) throws EventNotFoundException {
        Event event = this.eventRepository.findById(id).orElseThrow(EventNotFoundException::new);

        event.setImageUrl(dto.getImageUrl());

        this.eventRepository.save(event);
    }

    public ResponseGenerateUploadPresignedUrlDTO generateUploadPresignedUrl(RequestGenerateUploadPresignedUrlDTO dto) {
        String keyName = this.getUniqueFileNameWithExtension(dto.getOriginalFileName(), dto.getMetadata());

        System.out.println(keyName);

        String presignedUploadUrl = this.presignedUrlProvider.generateUploadPresignedUrl(
                bucketName,
                keyName,
                dto.getMetadata()
        );

        String imageUrl = getImageUrl(keyName);

        return new ResponseGenerateUploadPresignedUrlDTO(presignedUploadUrl, imageUrl);
    }

    private String getImageUrl(String keyName) {
        return "https://" + bucketName + ".s3.us-east-1.amazonaws.com/" + keyName;
    }

    private String getUniqueFileNameWithExtension(String keyName, Map<String, String> metadata) {
        String fileExtension = metadata.get("extension");
        if (fileExtension == null) {
            throw new InvalidEventFileExtensionException();
        }

        String fileName = keyName.split("\\.")[0]
                            .toLowerCase()
                            .replaceAll("[^a-z0-9_-]", "-");

        return UUID.randomUUID() + "-" + fileName + "." + fileExtension;
    }
}
