package com.odk.controller;

import com.odk.entity.Personne;
import com.odk.entity.Ticket;
import com.odk.enums.TypeCategorie;
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
@RequestMapping( produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "createTicket",consumes = APPLICATION_JSON_VALUE)
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @GetMapping("/mesTicket/{id}")
    public List<Ticket> getTicketsByApprenantId(@PathVariable Personne id) {
        return ticketService.getTicketsByApprenantId(id);
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Integer id) {
        return ticketService.getTicketById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Integer id, @RequestBody Ticket ticket) {
        ticket.setId(id);
        return ticketService.updateTicket(ticket);
    }

    @GetMapping("/categories")
    public TypeCategorie[] getCategories() {
        return TypeCategorie.values();
    }

    @GetMapping("/priorites")
    public TypePriorite[] getPriorites() {
        return TypePriorite.values();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable Integer id) {
        ticketService.deleteTicket(id);
    }

}
