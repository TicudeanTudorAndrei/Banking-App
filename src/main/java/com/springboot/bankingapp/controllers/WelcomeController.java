package com.springboot.bankingapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class WelcomeController {

    @GetMapping("/saluta")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello :3");
    }
}
