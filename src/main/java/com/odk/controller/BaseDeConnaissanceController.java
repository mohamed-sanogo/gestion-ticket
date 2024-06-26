package com.odk.controller;

import com.odk.entity.BaseDeConnaissance;
import com.odk.service.BaseDeConnaissanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class BaseDeConnaissanceController {

    private final BaseDeConnaissanceService baseDeConnaissanceService;

    public BaseDeConnaissanceController(BaseDeConnaissanceService baseDeConnaissanceService) {
        this.baseDeConnaissanceService = baseDeConnaissanceService;
    }

    @GetMapping(path = "listBase")
    public List<BaseDeConnaissance> getAll() {
        return baseDeConnaissanceService.getAll();
    }
}
