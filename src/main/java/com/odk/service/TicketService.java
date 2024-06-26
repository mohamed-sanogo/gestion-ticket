package com.odk.service;

import com.odk.entity.Personne;
import com.odk.entity.Ticket;
import com.odk.enums.TypePriorite;
import com.odk.enums.TypeStatut;
import com.odk.repository.PersonneRepository;
import com.odk.repository.TicketRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private TicketRepository ticketRepository;
    private PersonneRepository personneRepository;
    public TicketService(TicketRepository ticketRepository, PersonneRepository personneRepository) {
        this.ticketRepository = ticketRepository;
        this.personneRepository = personneRepository;
    }


    public Ticket createTicket(Ticket ticket) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        Personne apprenant = personneRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Personne non trouvé"));

        ticket.setApprenant(apprenant);
        ticket.setStatut(TypeStatut.Encours);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByApprenantId(Personne id) {
        return ticketRepository.findByApprenant(id);
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
