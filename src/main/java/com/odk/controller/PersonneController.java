package com.odk.controller;

import com.odk.config.JwtService;
import com.odk.dto.AuthenticationDTO;
import com.odk.entity.Personne;
import com.odk.enums.TypeRole;
import com.odk.service.PersonneService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@Slf4j
public class PersonneController {
    private PersonneService personneService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping(path = "inscription", produces = MediaType.APPLICATION_JSON_VALUE)
    public void inscription(@RequestBody Personne personne){
        this.personneService.inscription(personne);
        log.info("inscription");
    }

    @PostMapping(path = "connexion")
    public Map<String, String> connexion(@RequestBody AuthenticationDTO authenticationDTO){
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
        );
        if (authenticate.isAuthenticated()){
            return this.jwtService.generate(authenticationDTO.username());
        }
        return null;
    }

}
