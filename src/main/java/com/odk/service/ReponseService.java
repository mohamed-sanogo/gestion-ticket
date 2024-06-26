package com.odk.service;

import com.odk.entity.Personne;
import com.odk.entity.Reponse;
import com.odk.entity.Ticket;
import com.odk.enums.TypeStatut;
import com.odk.repository.PersonneRepository;
import com.odk.repository.ReponseRepository;
import com.odk.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ReponseService {
    private final ReponseRepository reponseRepository;
    private final TicketRepository ticketRepository;
    private final BaseDeConnaissanceService baseDeConnaissanceService;
    private PersonneRepository personneRepository;

    public ReponseService(ReponseRepository reponseRepository, TicketRepository ticketRepository, BaseDeConnaissanceService baseDeConnaissanceService, PersonneRepository personneRepository) {
        this.reponseRepository = reponseRepository;
        this.ticketRepository = ticketRepository;
        this.baseDeConnaissanceService = baseDeConnaissanceService;
        this.personneRepository = personneRepository;
    }

    @Transactional
    public Reponse createReponse(Reponse reponse) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        Personne formateur = personneRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Personne non trouvé"));

        reponse.setFormateur(formateur);

        Ticket ticket = ticketRepository.findById(reponse.getTicket().getId())
                .orElseThrow(() -> new EntityNotFoundException("Ticket no trouver"));
        ticket.setStatut(TypeStatut.Terminer);
        ticketRepository.save(ticket);
        reponse.setTicket(ticket);

        baseDeConnaissanceService.addToBaseDeConnaissance(ticket, reponse.getReponse());

        return reponseRepository.save(reponse);
    }
}
