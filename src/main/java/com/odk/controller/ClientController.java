package com.odk.controller;

import com.odk.entity.Client;
import com.odk.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "client")
public class ClientController {
    private ClientService clientService;
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createClient(@RequestBody Client client){
        this.clientService.createClient(client);

    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Client> rechercher(){
       return this.clientService.chercher();
    }
    @GetMapping(path="{id}", produces = APPLICATION_JSON_VALUE)
    public Client lire(@PathVariable Integer id){
       return this.clientService.lire(id);
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping(path = ("{id}"), consumes = APPLICATION_JSON_VALUE)
    public void updateClient(@PathVariable Integer id, @RequestBody Client client){
        this.clientService.updateClient(id, client);

    }

}
