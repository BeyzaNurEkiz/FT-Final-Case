package com.patika.ticketing.trip_service.service;

import com.patika.ticketing.trip_service.entity.Ticket;
import com.patika.ticketing.trip_service.entity.dto.request.TicketRequest;
import com.patika.ticketing.trip_service.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket reserveTicket(TicketRequest ticketRequest) {
        Ticket ticket = new Ticket();
        ticket.setUserId(ticketRequest.getUserId());
        ticket.setTrip(ticketRequest.getTrip());
        ticket.setQuantity(ticketRequest.getQuantity());
        ticket.setGender(ticketRequest.getGender());

        return ticketRepository.save(ticket);
    }
}
