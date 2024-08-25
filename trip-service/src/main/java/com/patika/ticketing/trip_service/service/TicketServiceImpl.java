package com.patika.ticketing.trip_service.service;

import com.patika.ticketing.trip_service.client.UserServiceClient;
import com.patika.ticketing.trip_service.entity.Ticket;
import com.patika.ticketing.trip_service.entity.dto.request.TicketRequest;
import com.patika.ticketing.trip_service.entity.dto.responce.UserInfoResponse;
import com.patika.ticketing.trip_service.exception.UnauthorizedException;
import com.patika.ticketing.trip_service.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserServiceClient userServiceClient;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, UserServiceClient userServiceClient) {
        this.ticketRepository = ticketRepository;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public Ticket reserveTicket(String token, TicketRequest ticketRequest) {
        // Kullanıcı bilgilerini user-service'den al
        UserInfoResponse userInfo = userServiceClient.getUserInfo(token);

        // Kullanıcı yetkilendirmesini kontrol et
        if (userInfo == null || !"USER".equals(userInfo.getRole())) {
            throw new UnauthorizedException("Geçersiz token veya yetkisiz kullanıcı");
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
