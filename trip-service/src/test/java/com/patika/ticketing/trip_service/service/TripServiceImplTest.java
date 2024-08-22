package com.patika.ticketing.trip_service.service;

import com.patika.ticketing.trip_service.entity.Trip;
import com.patika.ticketing.trip_service.entity.dto.request.TripCreateRequest;
import com.patika.ticketing.trip_service.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TripServiceImplTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripServiceImpl tripService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrip() {
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

        when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        Trip result = tripService.createTrip(request);

        assertNotNull(result);
        assertEquals("Istanbul", result.getDepartureCity());
        assertEquals("Ankara", result.getArrivalCity());
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    void testCancelTrip() {
        Trip trip = new Trip();
        trip.setId(1L);

        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));

        tripService.cancelTrip(1L);

        verify(tripRepository, times(1)).delete(trip);
    }
}
