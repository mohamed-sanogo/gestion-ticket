package com.odk.service;

import com.odk.entity.Personne;
import com.odk.entity.Ticket;
import com.odk.enums.TypePriorite;
import com.odk.enums.TypeStatut;
import com.odk.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private TicketRepository ticketRepository;
    private PersonneService personneService;

    public TicketService(TicketRepository ticketRepository, PersonneService personneService) {
        this.ticketRepository = ticketRepository;
        this.personneService = personneService;
    }

    //Modifier le statut du ticket
    public void updateTicketStatut(Integer id, TypeStatut statut) {
        Ticket ticket = this.ticketRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ticket non trouvé"));
        ticket.setStatut(statut);
        this.ticketRepository.save(ticket);
    }

    //Reponse a un ticket
    public void repondreAuTicket(Integer id, String reponse) {
        Ticket ticket = this.ticketRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ticket non trouvé"));
        // Logique pour ajouter la réponse (peut-être dans un champ `reponse` que tu devrais ajouter à l'entité Ticket)
        ticket.setStatut(TypeStatut.Terminer);
        this.ticketRepository.save(ticket);
    }

//    public void createTicket(Ticket ticket){
//        Personne personne = this.personneService.lireouCreer(ticket.getPersonne());
//        ticket.setPersonne(personne);
//        this.ticketRepository.save(ticket);
//    }

    public List<Ticket> chercher(TypePriorite type) {
        if(type == null){
            return this.ticketRepository.findAll();
        }else{
            return this.ticketRepository.findByPriorite(type);
        }

    }

    public void delete(Integer id) {
        this.ticketRepository.deleteById(id);
    }

    public void createTicket(Ticket ticket) {
        this.ticketRepository.save(ticket);
    }
}
