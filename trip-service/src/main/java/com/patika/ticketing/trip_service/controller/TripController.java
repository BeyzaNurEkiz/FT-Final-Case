package com.patika.ticketing.trip_service.controller;

import com.patika.ticketing.trip_service.entity.Trip;
import com.patika.ticketing.trip_service.entity.dto.request.TripCreateRequest;
import com.patika.ticketing.trip_service.entity.dto.request.TripSearchRequest;
import com.patika.ticketing.trip_service.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Trip> addTrip(@RequestBody TripCreateRequest tripRequest) {
        Trip trip = tripService.createTrip(tripRequest);
        return new ResponseEntity<>(trip, HttpStatus.CREATED);
    }

    @DeleteMapping("/cancel/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> cancelTrip(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        tripService.cancelTrip(id, token); // cancelTrip metodu token ile çağrılıyor
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Trip>> searchTrips(TripSearchRequest searchRequest) {
        List<Trip> trips = tripService.searchTrips(searchRequest);
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }
}
