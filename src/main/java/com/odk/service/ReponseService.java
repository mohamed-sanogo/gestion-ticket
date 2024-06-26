package com.odk.service;

import com.odk.entity.Reponse;
import com.odk.entity.Ticket;
import com.odk.enums.TypeStatut;
import com.odk.repository.ReponseRepository;
import com.odk.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReponseService {
    private final ReponseRepository reponseRepository;
    private final TicketRepository ticketRepository;
    private final BaseDeConnaissanceService baseDeConnaissanceService;

    public ReponseService(ReponseRepository reponseRepository, TicketRepository ticketRepository, BaseDeConnaissanceService baseDeConnaissanceService) {
        this.reponseRepository = reponseRepository;
        this.ticketRepository = ticketRepository;
        this.baseDeConnaissanceService = baseDeConnaissanceService;
    }

    @Transactional
    public Reponse createReponse(Reponse reponse) {
        Ticket ticket = reponse.getTicket();
        ticket.setStatut(TypeStatut.Terminer);

        ticketRepository.save(ticket);


        baseDeConnaissanceService.addToBaseDeConnaissance(ticket, reponse.getReponse());

        return reponseRepository.save(reponse);
    }
}
