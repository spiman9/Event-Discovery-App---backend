package com.example.EventDiscoveryApp.repository;

import com.example.EventDiscoveryApp.entity.RSVP;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RSVPRepository extends JpaRepository<RSVP, Long> {
    @Transactional
    void deleteByEventId(long id); // delete all rsvp entries related to a particular event
}