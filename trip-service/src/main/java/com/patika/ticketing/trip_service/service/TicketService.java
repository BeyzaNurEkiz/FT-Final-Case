package com.patika.ticketing.trip_service.service;

import com.patika.ticketing.trip_service.entity.Ticket;
import com.patika.ticketing.trip_service.entity.dto.request.TicketRequest;

public interface TicketService {
    Ticket reserveTicket(String token, TicketRequest ticketRequest);
}
