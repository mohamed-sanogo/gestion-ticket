package com.odk.controller;

import com.odk.entity.Ticket;
import com.odk.enums.TypePriorite;
import com.odk.enums.TypeRole;
import com.odk.enums.TypeStatut;
import com.odk.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    //Mise a jour du ticket
    @PutMapping(path = "/{id}/statut", consumes = APPLICATION_JSON_VALUE)
    public void updateTicketStatut(@PathVariable Integer id, @RequestBody TypeStatut statut, @RequestHeader("Role") String role){
        if(TypeRole.Apprenant.name().equals(role)){
            this.ticketService.updateTicketStatut(id, statut);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seul un APPRENANT peut mettre à jour le statut du ticket");
        }
    }

    //Reponse a un ticket
    @PutMapping(path = "/{id}/repondre", consumes = APPLICATION_JSON_VALUE)
    public void repondreAuTicket(@PathVariable Integer id, @RequestBody String reponse, @RequestHeader("Role") String role){
        if(TypeRole.Formateur.name().equals(role)){
            this.ticketService.repondreAuTicket(id, reponse);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seul un FORMATEUR peut répondre au ticket");
        }
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
