package com.odk.service;

import com.odk.entity.Personne;
import com.odk.entity.Role;
import com.odk.enums.TypeRole;
import com.odk.repository.PersonneRepository;
import com.odk.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Slf4j
public class PersonneService implements UserDetailsService {
    private PersonneRepository personneRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private RoleRepository roleRepository;
    private EmailService emailService;

    public PersonneService(PersonneRepository personneRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, EmailService emailService) {
        this.personneRepository = personneRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    private void validateEmail(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            throw new RuntimeException("Votre email est invalide");
        }
    }

    private void checkIfEmailExists(String email) {
        if (personneRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Votre email est déjà utilisé");
        }
    }

    public Role getOrCreateRole(TypeRole roleType) {
        return roleRepository.findByRole(roleType).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setRole(roleType);
            return roleRepository.save(newRole);
        });
    }

    private Personne createPersonne(Personne personne, TypeRole roleType) {
        validateEmail(personne.getEmail());
        checkIfEmailExists(personne.getEmail());
        String rawPassword = personne.getMdp();
        personne.setMdp(bCryptPasswordEncoder.encode(rawPassword));
        personne.setRole(getOrCreateRole(roleType));
        Personne savedPersonne = personneRepository.save(personne);

        // Envoi des identifiants par email
        emailService.indentifiantPersonne(personne.getEmail(), personne.getEmail(), rawPassword);

        return savedPersonne;
    }

    public void createAdmin(Personne admin) {
        createPersonne(admin, TypeRole.Admin);
    }

    public void createFormateur(Personne formateur) {
        createPersonne(formateur, TypeRole.Formateur);
    }

    public void createApprenant(Personne apprenant) {
        createPersonne(apprenant, TypeRole.Apprenant);
    }

    //Sur toutes les personnes
    public List<Personne> getAllPersonnes() {
        return personneRepository.findAll();
    }

    public Personne getPersonneById(Integer id) {
        return personneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée"));
    }

    public void deletePersonne(Integer id) {
        if (!personneRepository.existsById(id)) {
            throw new RuntimeException("Personne non trouvée");
        }
        personneRepository.deleteById(id);
    }


    // CRUD methods for Admins
    public List<Personne> getAllAdmins() {
        return personneRepository.findByRole(getOrCreateRole(TypeRole.Admin));
    }

    public Personne getAdminById(Integer id) {
        return personneRepository.findByIdAndRole(id, getOrCreateRole(TypeRole.Admin))
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));
    }

    public Personne updateAdmin(Integer id, Personne updatedPersonne) {
        return updatePersonneByRole(id, updatedPersonne, TypeRole.Admin);
    }

    public void deleteAdmin(Integer id) {
        deletePersonneByRole(id, TypeRole.Admin);
    }

    // CRUD methods for Formateurs
    public List<Personne> getAllFormateurs() {
        return personneRepository.findByRole(getOrCreateRole(TypeRole.Formateur));
    }

    public Personne getFormateurById(Integer id) {
        return personneRepository.findByIdAndRole(id, getOrCreateRole(TypeRole.Formateur))
                .orElseThrow(() -> new RuntimeException("Formateur non trouvé"));
    }

    public Personne updateFormateur(Integer id, Personne updatedPersonne) {
        return updatePersonneByRole(id, updatedPersonne, TypeRole.Formateur);
    }

    public void deleteFormateur(Integer id) {
        deletePersonneByRole(id, TypeRole.Formateur);
    }

    // CRUD methods for Apprenants
    public List<Personne> getAllApprenants() {
        return personneRepository.findByRole(getOrCreateRole(TypeRole.Apprenant));
    }

    public Personne getApprenantById(Integer id) {
        return personneRepository.findByIdAndRole(id, getOrCreateRole(TypeRole.Apprenant))
                .orElseThrow(() -> new RuntimeException("Apprenant non trouvé"));
    }

    public Personne updateApprenant(Integer id, Personne updatedPersonne) {
        return updatePersonneByRole(id, updatedPersonne, TypeRole.Apprenant);
    }

    public void deleteApprenant(Integer id) {
        deletePersonneByRole(id, TypeRole.Apprenant);
    }

    // Fonction de mise a jour par role
    private Personne updatePersonneByRole(Integer id, Personne updatedPersonne, TypeRole roleType) {
        Personne existingPersonne = personneRepository.findByIdAndRole(id, getOrCreateRole(roleType))
                .orElseThrow(() -> new RuntimeException(roleType + " non trouvé"));

        if (!existingPersonne.getEmail().equals(updatedPersonne.getEmail())) {
            checkIfEmailExists(updatedPersonne.getEmail());
        }

        existingPersonne.setNom(updatedPersonne.getNom());
        existingPersonne.setPrenom(updatedPersonne.getPrenom());
        existingPersonne.setEmail(updatedPersonne.getEmail());

        if (!updatedPersonne.getMdp().isEmpty()) {
            existingPersonne.setMdp(bCryptPasswordEncoder.encode(updatedPersonne.getMdp()));
        }

        existingPersonne.setActif(updatedPersonne.getActif());
        existingPersonne.setRole(getOrCreateRole(roleType));

        return personneRepository.save(existingPersonne);
    }

    private void deletePersonneByRole(Integer id, TypeRole roleType) {
        Personne personne = personneRepository.findByIdAndRole(id, getOrCreateRole(roleType))
                .orElseThrow(() -> new RuntimeException(roleType + " non trouvé"));
        personneRepository.delete(personne);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.personneRepository
                .findByEmail(username)
                .orElseThrow( ()-> new UsernameNotFoundException("Utilisateur indisponible"));
    }
}
