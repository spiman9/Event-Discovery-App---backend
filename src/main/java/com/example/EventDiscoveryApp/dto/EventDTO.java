package com.example.EventDiscoveryApp.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventDTO {
    private String title;
    private String description;
    private String category;
    private LocalDateTime eventDate;
    private String location;
    private int totalTickets;
}
