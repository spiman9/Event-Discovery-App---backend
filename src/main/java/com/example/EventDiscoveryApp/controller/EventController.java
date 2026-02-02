package com.example.EventDiscoveryApp.controller;

import com.example.EventDiscoveryApp.dto.EventDTO;
import com.example.EventDiscoveryApp.dto.EventDeleteResponse;
import com.example.EventDiscoveryApp.dto.RSVPDTO;
import com.example.EventDiscoveryApp.dto.RSVPresponse;
import com.example.EventDiscoveryApp.entity.Event;
import com.example.EventDiscoveryApp.entity.RSVP;
import com.example.EventDiscoveryApp.entity.User;
import com.example.EventDiscoveryApp.repository.UserRepository;
import com.example.EventDiscoveryApp.service.EventService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final UserRepository userRepository;


    //create a event
    @PostMapping
    public Event createEvent(@RequestBody EventDTO dto) {
        return eventService.createEvent(dto);
    }

    //get all event
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    //delete a event
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EventDeleteResponse deleteEvent(@PathVariable long id){
        return eventService.deleteEvent(id);
    }

    //search the event based on the category in request params
    @GetMapping("/search")
    public List<Event> filterByCategory(@RequestParam String category) {
        return eventService.getByCategory(category);
    }

    //filter based on the dates events
    @GetMapping("/filterDate")
    public List<Event> filterByDate(@RequestParam String start,
                                    @RequestParam String end) {
        return eventService.getByDateRange(LocalDateTime.parse(start),
                LocalDateTime.parse(end));
    }

    //user wants to book based on the event and number of tickets
    @PostMapping("/rsvp")
    public RSVPresponse rsvp(@RequestBody RSVPDTO dto , @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found: " + userDetails.getUsername()));
        dto.setUserId(user.getId());
        return eventService.rsvpEvent(dto);
    }
}
