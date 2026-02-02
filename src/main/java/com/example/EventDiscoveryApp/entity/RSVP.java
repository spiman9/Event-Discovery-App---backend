package com.example.EventDiscoveryApp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rsvps")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RSVP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Event event;

    private int ticketsBooked;
}
