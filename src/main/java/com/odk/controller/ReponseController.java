package com.odk.controller;

import com.odk.entity.Reponse;
import com.odk.service.ReponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ReponseController {

    private final ReponseService reponseService;

    public ReponseController(ReponseService reponseService) {
        this.reponseService = reponseService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "newReponse")
    public Reponse createReponse(@RequestBody Reponse reponse) {
        return reponseService.createReponse(reponse);
    }
}
