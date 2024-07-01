package com.odk.controller;

import com.odk.entity.Personne;
import com.odk.enums.TypeRole;
import com.odk.service.PersonneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonneController {
    private PersonneService personneService;
    public PersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }

    // Controller pour Personnes
    @GetMapping(path = "personne")
    public List<Personne> getAllPersonnes() {
        return personneService.getAllPersonnes();
    }
    @GetMapping("/personne/{id}")
    public Personne getPersonneById(@PathVariable Integer id) {
        return personneService.getPersonneById(id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/personne/{id}")
    public void deletePersonne(@PathVariable Integer id) {
        personneService.deletePersonne(id);
    }


    //Controller pour Admin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/admin", consumes = APPLICATION_JSON_VALUE)
    public void createAdmin(@RequestBody Personne admin){
            this.personneService.createAdmin(admin);
    }

    @PutMapping(path = "/admin/{id}", consumes = APPLICATION_JSON_VALUE)
    public Personne updateAdmin(@PathVariable Integer id, @RequestBody Personne admin) {
        admin.setRole(personneService.getOrCreateRole(TypeRole.Admin));
        return personneService.updateAdmin(id, admin);
    }

    //Controller pour Formateur
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/formateur", consumes = APPLICATION_JSON_VALUE)
    public void createFormateur(@RequestBody Personne formateur){
        this.personneService.createFormateur(formateur);
    }

    @PutMapping(path = "/formateur/{id}", consumes = APPLICATION_JSON_VALUE)
    public Personne updateFormateur(@PathVariable Integer id, @RequestBody Personne formateur) {
        formateur.setRole(personneService.getOrCreateRole(TypeRole.Formateur));
        return personneService.updateFormateur(id, formateur);
    }

    //Controller pour Apprenant
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/apprenant", consumes = APPLICATION_JSON_VALUE)
    public void createApprenant(@RequestBody Personne apprenant){
        this.personneService.createApprenant(apprenant);
    }

    @PutMapping(path = "/apprenant/{id}", consumes = APPLICATION_JSON_VALUE)
    public Personne updateApprenant(@PathVariable Integer id, @RequestBody Personne apprenant) {
        apprenant.setRole(personneService.getOrCreateRole(TypeRole.Apprenant));
        return personneService.updateApprenant(id, apprenant);
    }

}
