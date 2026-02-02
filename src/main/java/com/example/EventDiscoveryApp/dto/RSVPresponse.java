package com.example.EventDiscoveryApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RSVPresponse {
    private long ticketBooked;
    private String status;
}
