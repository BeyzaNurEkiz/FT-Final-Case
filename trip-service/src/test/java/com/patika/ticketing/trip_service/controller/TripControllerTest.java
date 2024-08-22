package com.patika.ticketing.trip_service.controller;

import com.patika.ticketing.trip_service.entity.Trip;
import com.patika.ticketing.trip_service.entity.dto.request.TripCreateRequest;
import com.patika.ticketing.trip_service.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TripControllerTest {

    @Mock
    private TripService tripService;

    @InjectMocks
    private TripController tripController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTrip() {
        TripCreateRequest request = new TripCreateRequest();
        request.setDepartureCity("Istanbul");
        request.setDestinationCity("Ankara");
        request.setDepartureDate(LocalDate.of(2024, 9, 1));
        request.setVehicleType("Bus");
        request.setCapacity(50);

        Trip trip = new Trip();
        trip.setId(1L);
        trip.setDepartureCity("Istanbul");
        trip.setArrivalCity("Ankara");
        trip.setTripDate(LocalDate.of(2024, 9, 1));
        trip.setVehicleType("Bus");
        trip.setCapacity(50);

        when(tripService.createTrip(request)).thenReturn(trip);

        ResponseEntity<Trip> response = tripController.addTrip(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(trip, response.getBody());
        verify(tripService, times(1)).createTrip(request);
    }

    @Test
    void testCancelTrip() {
        ResponseEntity<Void> response = tripController.cancelTrip(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tripService, times(1)).cancelTrip(1L);
    }
}
