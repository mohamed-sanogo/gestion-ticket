package com.odk.service;

import com.odk.entity.Personne;
import com.odk.enums.TypeRole;
import com.odk.repository.PersonneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonneService {
    private PersonneRepository personneRepository;

    public PersonneService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }


    public void createPersonne(Personne personne){
        Personne personneDansLaBBD = this.personneRepository.findByEmail(personne.getEmail());
        if(personneDansLaBBD == null){
            this.personneRepository.save(personne);
        }
    }

    //Creation d'un formateur
    public void createFormateur(Personne formateur){
        if (formateur.getRole() == TypeRole.Formateur) {
            Personne existingPerson = this.personneRepository.findByEmail(formateur.getEmail());
            if(existingPerson == null){
                this.personneRepository.save(formateur);
            }
        } else {
            throw new IllegalArgumentException("Le rôle de la personne doit être Formateur");
        }
    }

    //Creation d'un apprenant
    public void createApprenant(Personne apprenant){
        if (apprenant.getRole() == TypeRole.Apprenant) {
            Personne existingPerson = this.personneRepository.findByEmail(apprenant.getEmail());
            if(existingPerson == null){
                this.personneRepository.save(apprenant);
            }
        } else {
            throw new IllegalArgumentException("Le rôle de la personne doit être APPRENANT");
        }
    }


    public List<Personne> chercher(){
        return this.personneRepository.findAll();
    }

    public Personne lire(Integer id) {
        Optional<Personne> optionalPersonne = this.personneRepository.findById(id);
        //Verfication pour savoir si client existe ou pas
        return optionalPersonne.orElse(null);
    }
    public Personne lireouCreer(Personne personneAceer){
        Personne personneDansLaBBD = this.personneRepository.findByEmail(personneAceer.getEmail());
        if(personneDansLaBBD == null){
            this.personneRepository.save(personneAceer);
        }
        return personneDansLaBBD;
    }

    public void updatePersonne(Integer id, Personne personne) {
       Personne lePersonneDanasLaBdd = this.lire(id);
       if(lePersonneDanasLaBdd.getId() == personne.getId()){
           lePersonneDanasLaBdd.setPrenom(personne.getPrenom());
           lePersonneDanasLaBdd.setNom(personne.getNom());
           lePersonneDanasLaBdd.setEmail(personne.getEmail());
           lePersonneDanasLaBdd.setMdp(personne.getMdp());
           lePersonneDanasLaBdd.setRole(personne.getRole());
           this.personneRepository.save(lePersonneDanasLaBdd);
       }

    }
}
