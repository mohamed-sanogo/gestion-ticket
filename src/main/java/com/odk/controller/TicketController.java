package com.odk.controller;

import com.odk.entity.Ticket;
import com.odk.enums.TypePriorite;
import com.odk.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path = "ticket", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {
    private TicketService ticketService;
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createTicket(@RequestBody Ticket ticket){
        //Analyse
        ticket.setPriorite(TypePriorite.Urgent);
        if(ticket.getTitre().contains("pas")){
            ticket.setPriorite(TypePriorite.MoinsUrgent);
        }
        this.ticketService.createTicket(ticket);
    }

    @GetMapping
    public @ResponseBody List<Ticket> rechercher(@RequestParam(required = false) TypePriorite type){
        return this.ticketService.chercher(type);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(path = ("{id}"))
    public void deleteTicket(@PathVariable Integer id){
        this.ticketService.delete(id);
    }

}
