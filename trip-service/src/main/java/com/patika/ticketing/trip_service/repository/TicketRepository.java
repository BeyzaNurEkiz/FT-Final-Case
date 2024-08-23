package com.patika.ticketing.trip_service.repository;

import com.patika.ticketing.trip_service.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
