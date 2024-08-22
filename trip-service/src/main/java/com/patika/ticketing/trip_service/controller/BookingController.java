package com.patika.ticketing.trip_service.controller;

import com.patika.ticketing.trip_service.entity.Ticket;
import com.patika.ticketing.trip_service.entity.Trip;
import com.patika.ticketing.trip_service.entity.dto.request.TicketRequest;
import com.patika.ticketing.trip_service.entity.dto.request.TripSearchRequest;
import com.patika.ticketing.trip_service.service.TicketService;
import com.patika.ticketing.trip_service.service.TicketServiceImpl;
import com.patika.ticketing.trip_service.service.TripService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final TicketService ticketService;
    private final TripService tripService;

    @Autowired
    public BookingController(TicketService ticketService, TripService tripService) {
        this.ticketService = ticketService;
        this.tripService = tripService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<Ticket> reserveTicket(@Valid @RequestBody TicketRequest ticketRequest) {
        Ticket reservedTicket = ticketService.reserveTicket(ticketRequest);
        return ResponseEntity.ok(reservedTicket);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Trip>> searchTrips(@Valid TripSearchRequest searchRequest) {
        List<Trip> trips = tripService.searchTrips(searchRequest);
        return ResponseEntity.ok(trips);
    }
}
