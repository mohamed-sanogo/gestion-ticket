package com.odk.service;

import com.odk.entity.Personne;
import com.odk.entity.Ticket;
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
    private EmailService emailService;

    public TicketService(TicketRepository ticketRepository, PersonneRepository personneRepository, EmailService emailService) {
        this.ticketRepository = ticketRepository;
        this.personneRepository = personneRepository;
        this.emailService = emailService;
    }

    //recuperation du mail de la personne autentifier
    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public Ticket createTicket(Ticket ticket) {
        String email = getCurrentUserEmail();
        Personne apprenant = personneRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));


        ticket.setApprenant(apprenant);
        ticket.setStatut(TypeStatut.Encours);
        Ticket t = ticketRepository.save(ticket);
        String message = "Un nouveau ticket a été créé par " + apprenant.getPrenom() + " " + apprenant.getNom() + ".";
        emailService.emailFormateurs(message);
        return t;

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
