package com.odk.service;

import com.odk.entity.Personne;
import com.odk.entity.Ticket;
import com.odk.enums.TypePriorite;
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




    public void createTicket(Ticket ticket){
        Personne personne = this.personneService.lireouCreer(ticket.getPersonne());
        ticket.setPersonne(personne);
        this.ticketRepository.save(ticket);
    }

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
}
