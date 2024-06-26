package com.odk.service;

import com.odk.entity.Personne;
import com.odk.entity.Ticket;
import com.odk.enums.TypePriorite;
import com.odk.enums.TypeStatut;
import com.odk.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private TicketRepository ticketRepository;
    public TicketService(TicketRepository ticketRepository, PersonneService personneService) {
        this.ticketRepository = ticketRepository;
    }


    public Ticket createTicket(Ticket ticket) {
        ticket.setStatut(TypeStatut.Encours);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByPersonneId(Integer id) {
        return ticketRepository.findByPersonneId(id);
    }

    public Optional<Ticket> getTicketById(Integer id) {
        return ticketRepository.findById(id);
    }

    public Ticket updateTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Integer id) {
        ticketRepository.deleteById(id);
    }

 }
