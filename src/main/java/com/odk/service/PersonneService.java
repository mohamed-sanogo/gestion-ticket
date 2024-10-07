package com.odk.service;

import com.odk.entity.Personne;
import com.odk.entity.Role;
import com.odk.enums.TypeRole;
import com.odk.repository.PersonneRepository;
import lombok.AllArgsConstructor;
import org.apache.el.util.Validation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonneService implements UserDetailsService {

    private PersonneRepository personneRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void inscription(Personne personne){
        if(!personne.getEmail().contains("@")){
            throw new RuntimeException("Votre email est invalid");
        }
        if(!personne.getEmail().contains(".")){
            throw new RuntimeException("Votre email est invalid");
        }
        Optional<Personne> personneOptional = this.personneRepository.findByEmail(personne.getEmail());
        if(personneOptional.isPresent()){
            throw new RuntimeException("Votre email est déjà utilisé");
        }
        String mdpCrypte = this.bCryptPasswordEncoder.encode(personne.getMdp());
        personne.setMdp(mdpCrypte);

        Role rolePersonne = new Role();
        rolePersonne.setRole(TypeRole.APPRENANT);
        personne.setRole(rolePersonne);


        personne =  this.personneRepository.save(personne);
    }

    /*public void activation(Map<String, String> activation){
        Validation validation = this.validationService.LireEnfonctionDuCode(activation.get("code"));
        if(Instant.now().isAfter(validation.getExpiration())){
            throw new RuntimeException("Votre code a expiré");
        }
        Utilisateur utilisateurActiver = this.utiisateurRepository.findById(validation.getUtilisateur().getId()).orElseThrow(()-> new RuntimeException("utilisateur inconnu"));
        utilisateurActiiver.setActif(true);
        this.utilisateurRepository.save(utilisateurActiver);
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.personneRepository
                .findByEmail(username)
                .orElseThrow( ()-> new UsernameNotFoundException("Utilisateur indisponible"));
    }

}
