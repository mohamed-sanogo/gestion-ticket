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


}
