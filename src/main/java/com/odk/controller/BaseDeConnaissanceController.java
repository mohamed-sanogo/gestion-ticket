package com.odk.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.odk.entity.BaseDeConnaissance;
import com.odk.service.BaseDeConnaissanceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class BaseDeConnaissanceController {

    private final BaseDeConnaissanceService baseDeConnaissanceService;

    public BaseDeConnaissanceController(BaseDeConnaissanceService baseDeConnaissanceService) {
        this.baseDeConnaissanceService = baseDeConnaissanceService;
    }

    @GetMapping(path = "listBase")
    public List<BaseDeConnaissance> getAll() {
        return baseDeConnaissanceService.getAll();
    }
    @PostMapping(path = "create")
    public BaseDeConnaissance createBase(@RequestBody BaseDeConnaissance base){
        return baseDeConnaissanceService.createBase(base);
    }

    @PutMapping(path = "updateBase/{id}")
    public BaseDeConnaissance updateBase(@PathVariable Integer id, @RequestBody BaseDeConnaissance base){
        base.setId(id);
        return baseDeConnaissanceService.updateBase(base);
    }

    @DeleteMapping(path = "deleteBase/{id}")
    public void deleteBase(@PathVariable Integer id){
         baseDeConnaissanceService.deleteBase(id);
    }
}
