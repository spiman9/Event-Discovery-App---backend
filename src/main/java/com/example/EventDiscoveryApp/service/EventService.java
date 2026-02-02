package com.example.EventDiscoveryApp.service;

import com.example.EventDiscoveryApp.dto.EventDTO;
import com.example.EventDiscoveryApp.dto.EventDeleteResponse;
import com.example.EventDiscoveryApp.dto.RSVPDTO;
import com.example.EventDiscoveryApp.dto.RSVPresponse;
import com.example.EventDiscoveryApp.entity.Event;
import com.example.EventDiscoveryApp.entity.RSVP;
import com.example.EventDiscoveryApp.entity.User;
import com.example.EventDiscoveryApp.repository.EventRepository;
import com.example.EventDiscoveryApp.repository.RSVPRepository;
import com.example.EventDiscoveryApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    @Autowired
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RSVPRepository rsvpRepository;

    public EventDeleteResponse deleteEvent(long id) {
        if(!eventRepository.existsById(id)){
            return new EventDeleteResponse("FAILED" ,"Not able to locate the id that you want to delete" );
        }
        rsvpRepository.deleteByEventId(id); // delete all the rsvp first
        eventRepository.deleteById(id); // delete the event now
        return new EventDeleteResponse("SUCCESS" ,"Deleted the event successfully" );
    }

    public Event createEvent(EventDTO dto) {
        log.info("EventService | createEvent - creating event");

        Event event = Event.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .eventDate(dto.getEventDate())
                .location(dto.getLocation())
                .totalTickets(dto.getTotalTickets())
                .availableTickets(dto.getTotalTickets())
                .build();

        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        log.info("EventService | getAllEvents - fetching events");
        return eventRepository.findAll();
    }

    public List<Event> getByCategory(String category) {
        log.info("EventService | getByCategory - filtering by category");
        return eventRepository.findByCategoryIgnoreCase(category);
    }

    public List<Event> getByDateRange(LocalDateTime start, LocalDateTime end) {
        log.info("EventService | getByDateRange - filtering by date");
        return eventRepository.findByEventDateBetween(start, end);
    }

    @Transactional
    public RSVPresponse rsvpEvent(RSVPDTO dto) {
        log.info("EventService | rsvpEvent - booking tickets");

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getAvailableTickets() < dto.getTicketsBooked()) {
            RSVPresponse failedres =  new RSVPresponse();
            failedres.setStatus("Failed | Insufficient tickets");
            failedres.setTicketBooked(dto.getTicketsBooked());
            return failedres;
        }

        event.setAvailableTickets(event.getAvailableTickets() - dto.getTicketsBooked());

        RSVP rsvp = RSVP.builder()
                .user(user)
                .event(event)
                .ticketsBooked(dto.getTicketsBooked())
                .build();

        RSVP rsvpRes = null;
        try{
            rsvpRes  = rsvpRepository.save(rsvp);
            return new RSVPresponse(rsvp.getTicketsBooked(), "SUCCESS | tickets booked successfully");
        } catch (Exception e) {
            return  new RSVPresponse(rsvp.getTicketsBooked() , "FAILED | tickets not booked" );
        }

    }
}
