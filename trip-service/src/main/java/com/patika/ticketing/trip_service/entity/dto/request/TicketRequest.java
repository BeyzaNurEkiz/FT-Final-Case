package com.patika.ticketing.trip_service.entity.dto.request;

import com.patika.ticketing.trip_service.entity.Trip;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Trip cannot be null")
    private Trip trip;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @Size(min = 1, max = 1, message = "Gender should be a single character")
    private String gender;
}
