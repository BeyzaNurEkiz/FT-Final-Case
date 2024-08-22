package com.patika.ticketing.trip_service.entity.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TripCreateRequest {
    @NotBlank(message = "Departure city cannot be empty")
    private String departureCity;

    @NotBlank(message = "Destination city cannot be empty")
    private String destinationCity;

    @NotNull(message = "Departure date cannot be null")
    @Future(message = "Departure date must be in the future")
    private LocalDate departureDate;

    @NotBlank(message = "Vehicle type cannot be empty")
    private String vehicleType;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

}
