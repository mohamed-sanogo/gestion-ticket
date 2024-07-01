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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ReponseService {
    private final ReponseRepository reponseRepository;
    private final TicketRepository ticketRepository;
    private final BaseDeConnaissanceService baseDeConnaissanceService;
    private PersonneRepository personneRepository;
    private EmailService emailService;

    public ReponseService(ReponseRepository reponseRepository, TicketRepository ticketRepository, BaseDeConnaissanceService baseDeConnaissanceService, PersonneRepository personneRepository, EmailService emailService) {
        this.reponseRepository = reponseRepository;
        this.ticketRepository = ticketRepository;
        this.baseDeConnaissanceService = baseDeConnaissanceService;
        this.personneRepository = personneRepository;
        this.emailService = emailService;
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

        Personne apprenant = ticket.getApprenant();
        String subjet = "Reponse au Ticket ";
        String message = "Votre Ticket a été resolue connecter vous pour verifier";
        emailService.sendEmail(apprenant.getEmail(), subjet, message);
        return reponseRepository.save(reponse);
    }
}
