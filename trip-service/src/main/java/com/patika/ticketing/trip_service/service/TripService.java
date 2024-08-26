package com.patika.ticketing.trip_service.service;

import com.patika.ticketing.trip_service.entity.Trip;
import com.patika.ticketing.trip_service.entity.dto.request.TripCreateRequest;
import com.patika.ticketing.trip_service.entity.dto.request.TripSearchRequest;

import java.util.List;

public interface  TripService {
    Trip createTrip(TripCreateRequest tripRequest);
    void cancelTrip(Long tripId, String token);
    List<Trip> searchTrips(TripSearchRequest searchRequest);

}
