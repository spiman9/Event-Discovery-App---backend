package com.example.EventDiscoveryApp.repository;

import com.example.EventDiscoveryApp.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCategoryIgnoreCase(String category);

    List<Event> findByEventDateBetween(LocalDateTime start, LocalDateTime end);
}
