package com.patika.ticketing.trip_service.entity.dto.request;

import jakarta.validation.constraints.Future;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TripSearchRequest {
    private String departureCity;
    private String destinationCity;
    private String vehicleType;
    @Future(message = "Departure date must be in the future")
    private LocalDate departureDate;


}
