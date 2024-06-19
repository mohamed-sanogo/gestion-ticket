package com.odk.service;

import com.odk.entity.Client;
import com.odk.entity.Ticket;
import com.odk.enums.TypePriorite;
import com.odk.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private TicketRepository ticketRepository;
    private ClientService clientService;

    public TicketService(TicketRepository ticketRepository, ClientService clientService) {
        this.ticketRepository = ticketRepository;
        this.clientService = clientService;
    }




    public void createTicket(Ticket ticket){
        Client client = this.clientService.lireouCreer(ticket.getClient());
        ticket.setClient(client);
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
