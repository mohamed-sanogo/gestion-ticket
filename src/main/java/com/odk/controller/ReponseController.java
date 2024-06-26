package com.odk.controller;

import com.odk.entity.Reponse;
import com.odk.service.ReponseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping
public class ReponseController {

    private final ReponseService reponseService;

    public ReponseController(ReponseService reponseService) {
        this.reponseService = reponseService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "createReponse",consumes = APPLICATION_JSON_VALUE)
    public Reponse createReponse(@RequestBody Reponse reponse) {
        return reponseService.createReponse(reponse);
    }
}
