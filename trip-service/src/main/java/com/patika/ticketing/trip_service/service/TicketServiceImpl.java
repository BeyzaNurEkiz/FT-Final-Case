package com.patika.ticketing.trip_service.service;

import com.patika.ticketing.trip_service.entity.Ticket;
import com.patika.ticketing.trip_service.entity.dto.request.TicketRequest;
import com.patika.ticketing.trip_service.exception.UnauthorizedException;
import com.patika.ticketing.trip_service.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final AuthService authService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, AuthService authService) {
        this.ticketRepository = ticketRepository;
        this.authService = authService;
    }

    @Override
    public Ticket reserveTicket(String token, TicketRequest ticketRequest) {
        // JWT token'ı doğrulama
        if (!authService.validateToken(token)) {
            throw new UnauthorizedException("Invalid token");
        }

        // Bilet rezervasyonu yapma işlemi
        Ticket ticket = new Ticket();
        ticket.setUserId(ticketRequest.getUserId());
        ticket.setTrip(ticketRequest.getTrip());
        ticket.setQuantity(ticketRequest.getQuantity());
        ticket.setGender(ticketRequest.getGender());

        return ticketRepository.save(ticket);
    }
}
