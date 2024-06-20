package com.odk.controller;

import com.odk.entity.Personne;
import com.odk.service.PersonneService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "personne")
public class PersonneController {
    private PersonneService personneService;
    public PersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }


    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createClient(@RequestBody Personne personne){
        this.personneService.createPersonne(personne);

    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Personne> rechercher(){
       return this.personneService.chercher();
    }
    @GetMapping(path="{id}", produces = APPLICATION_JSON_VALUE)
    public Personne lire(@PathVariable Integer id){
       return this.personneService.lire(id);
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping(path = ("{id}"), consumes = APPLICATION_JSON_VALUE)
    public void updateClient(@PathVariable Integer id, @RequestBody Personne personne){
        this.personneService.updatePersonne(id, personne);

    }

}
