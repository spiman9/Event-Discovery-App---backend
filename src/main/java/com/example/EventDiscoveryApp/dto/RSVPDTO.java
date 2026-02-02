package com.example.EventDiscoveryApp.dto;

import lombok.Data;

@Data
public class RSVPDTO {
    private Long userId;
    private Long eventId;
    private int ticketsBooked;
}
