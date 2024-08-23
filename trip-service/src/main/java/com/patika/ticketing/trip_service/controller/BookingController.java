package com.patika.ticketing.trip_service.controller;

import com.patika.ticketing.trip_service.entity.Ticket;
import com.patika.ticketing.trip_service.entity.Trip;
import com.patika.ticketing.trip_service.entity.dto.request.TicketRequest;
import com.patika.ticketing.trip_service.entity.dto.request.TripSearchRequest;
import com.patika.ticketing.trip_service.exception.UnauthorizedException;
import com.patika.ticketing.trip_service.service.AuthService;
import com.patika.ticketing.trip_service.service.TicketService;
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
    private final AuthService authService;

    @Autowired
    public BookingController(TicketService ticketService, TripService tripService, AuthService authService) {
        this.ticketService = ticketService;
        this.tripService = tripService;
        this.authService = authService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<Ticket> reserveTicket(@RequestHeader("Authorization") String token, @RequestBody TicketRequest ticketRequest) {
        Ticket reservedTicket = ticketService.reserveTicket(token, ticketRequest);
        return ResponseEntity.ok(reservedTicket);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Trip>> searchTrips(@RequestHeader("Authorization") String token, @Valid TripSearchRequest searchRequest) {
        if (!authService.validateToken(token)) {
            throw new UnauthorizedException("Invalid token");
        }
        List<Trip> trips = tripService.searchTrips(searchRequest);
        return ResponseEntity.ok(trips);
    }
}
