package com.paulo.hurry_up.repository;

import com.paulo.hurry_up.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT e FROM Event e WHERE " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page<Event> findAllByEventName(@Param("q") String q, Pageable pageable);
}
