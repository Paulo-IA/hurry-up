package com.paulo.hurry_up.repository;

import com.paulo.hurry_up.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
