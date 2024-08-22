package com.patika.ticketing.trip_service.service;


import com.patika.ticketing.trip_service.entity.Trip;
import com.patika.ticketing.trip_service.entity.dto.request.TripCreateRequest;
import com.patika.ticketing.trip_service.entity.dto.request.TripSearchRequest;
import com.patika.ticketing.trip_service.repository.TripRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public Trip createTrip(TripCreateRequest tripRequest) {
        Trip trip = new Trip();
        trip.setDepartureCity(tripRequest.getDepartureCity());
        trip.setArrivalCity(tripRequest.getDestinationCity());
        trip.setTripDate(tripRequest.getDepartureDate());
        trip.setVehicleType(tripRequest.getVehicleType());
        trip.setCapacity(tripRequest.getCapacity());
        tripRepository.save(trip);
        return trip;
    }

    @Override
    public void cancelTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        tripRepository.delete(trip); // Alternatif: trip.setActive(false); ve pasif hale getirme.
    }

    @Override
    public List<Trip> searchTrips(TripSearchRequest searchRequest) {
        return tripRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchRequest.getDepartureCity() != null) {
                predicates.add(criteriaBuilder.equal(root.get("departureCity"), searchRequest.getDepartureCity()));
            }
            if (searchRequest.getDestinationCity() != null) {
                predicates.add(criteriaBuilder.equal(root.get("arrivalCity"), searchRequest.getDestinationCity())); // destination yerine arrival kullanılıyor
            }
            if (searchRequest.getVehicleType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("vehicleType"), searchRequest.getVehicleType()));
            }
            if (searchRequest.getDepartureDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("tripDate"), searchRequest.getDepartureDate())); // departure yerine tripDate kullanılıyor
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
