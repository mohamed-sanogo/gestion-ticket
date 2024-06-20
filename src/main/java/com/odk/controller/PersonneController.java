package com.odk.controller;

import com.odk.entity.Personne;
import com.odk.enums.TypeRole;
import com.odk.service.PersonneService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    //Controller pour créér un Formateur
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/formateur", consumes = APPLICATION_JSON_VALUE)
    public void createFormateur(@RequestBody Personne formateur, @RequestHeader("Role") String role){
        if(TypeRole.Admin.name().equals(role)){
            formateur.setRole(TypeRole.Formateur);
            this.personneService.createFormateur(formateur);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seul un ADMIN peut créer un formateur");
        }
    }

    //Controller pour créér un Apprenant
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/apprenant", consumes = APPLICATION_JSON_VALUE)
    public void createApprenant(@RequestBody Personne apprenant, @RequestHeader("Role") String role){
        if(TypeRole.Formateur.name().equals(role)){
            apprenant.setRole(TypeRole.Apprenant);
            this.personneService.createApprenant(apprenant);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seul un FORMATEUR peut créer un apprenant");
        }
    }


    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createPersonne(@RequestBody Personne personne){
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
