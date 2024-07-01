package com.odk.service;


import com.odk.entity.Personne;
import com.odk.entity.Role;
import com.odk.enums.TypeRole;
import com.odk.repository.PersonneRepository;
import com.odk.repository.RoleRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailService {

    private final RoleRepository roleRepository;
    @Value("${spring.mail.username}")
    private String fromEmail;

    private JavaMailSender javaMailSender;
    private PersonneRepository personneRepository;

    public EmailService(JavaMailSender javaMailSender, PersonneRepository personneRepository, RoleRepository roleRepository) {
        this.javaMailSender = javaMailSender;
        this.personneRepository = personneRepository;
        this.roleRepository = roleRepository;
    }

    public void indentifiantPersonne(String to, String username, String password) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Vos identifiants de compte");
            mimeMessageHelper.setText("Votre nom d'utilisateur : " + username + "\nVotre mot de passe : " + password);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void emailFormateurs(String message) {
        String subject = "Nouveau Ticket ";
        List<Personne> formateurs = personneRepository.findByRole(getOrCreateRole(TypeRole.Formateur));
        for (Personne formateur : formateurs) {
            sendEmail(formateur.getEmail(), subject, message);
        }
    }

    public Role getOrCreateRole(TypeRole roleType) {
        return roleRepository.findByRole(roleType).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setRole(roleType);
            return roleRepository.save(newRole);
        });
    }


}
