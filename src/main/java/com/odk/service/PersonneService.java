package com.odk.service;

import com.odk.entity.Personne;
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

    public List<Personne> chercher(){
        return this.personneRepository.findAll();
    }

    public Personne lire(Integer id) {
        Optional<Personne> optionalClient = this.personneRepository.findById(id);
        //Verfication pour savoir si client existe ou pas
        return optionalClient.orElse(null);
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
