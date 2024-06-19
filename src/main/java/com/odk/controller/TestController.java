package com.odk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="test")
public class TestController {
    @GetMapping(path="test")
   public String getString(){
        return "Hello word !";
    }

    @GetMapping()
    public List<String> getList(){
        return List.of("Hello word !", "Hello word !");
    }
}
