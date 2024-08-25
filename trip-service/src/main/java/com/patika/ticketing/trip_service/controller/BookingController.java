package com.patika.ticketing.trip_service.controller;

import com.patika.ticketing.trip_service.client.UserServiceClient;
import com.patika.ticketing.trip_service.entity.Ticket;
import com.patika.ticketing.trip_service.entity.Trip;
import com.patika.ticketing.trip_service.entity.dto.request.TicketRequest;
import com.patika.ticketing.trip_service.entity.dto.request.TripSearchRequest;
import com.patika.ticketing.trip_service.entity.dto.responce.UserInfoResponse;
import com.patika.ticketing.trip_service.exception.UnauthorizedException;
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
    private final UserServiceClient userServiceClient;

    @Autowired
    public BookingController(TicketService ticketService, TripService tripService, UserServiceClient userServiceClient) {
        this.ticketService = ticketService;
        this.tripService = tripService;
        this.userServiceClient = userServiceClient;
    }

    @PostMapping("/reserve")
    public ResponseEntity<Ticket> reserveTicket(@RequestHeader("Authorization") String token, @RequestBody TicketRequest ticketRequest) {
        // Kullanıcıyı user-service'den al
        UserInfoResponse userInfo = userServiceClient.getUserInfo(token);

        // Kullanıcı rolüne göre işlemi gerçekleştir
        if (!"USER".equals(userInfo.getRole())) {
            throw new UnauthorizedException("Sadece USER rolüne sahip kullanıcılar rezervasyon yapabilir.");
        }

        Ticket reservedTicket = ticketService.reserveTicket(token, ticketRequest);
        return ResponseEntity.ok(reservedTicket);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Trip>> searchTrips(@RequestHeader("Authorization") String token, @Valid TripSearchRequest searchRequest) {
        // Kullanıcıyı user-service'den al
        UserInfoResponse userInfo = userServiceClient.getUserInfo(token);

        // Token doğrulama ve rol kontrolü
        if (userInfo == null || !"USER".equals(userInfo.getRole())) {
            throw new UnauthorizedException("Geçersiz token veya yetkisiz kullanıcı");
        }

        List<Trip> trips = tripService.searchTrips(searchRequest);
        return ResponseEntity.ok(trips);
    }
}
