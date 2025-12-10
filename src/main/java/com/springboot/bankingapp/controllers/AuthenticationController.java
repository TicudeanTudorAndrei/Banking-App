package com.springboot.bankingapp.controllers;

import com.springboot.bankingapp.responses.AuthenticationResponse;
import com.springboot.bankingapp.domain.entities.requests.AuthenticateRequest;
import com.springboot.bankingapp.domain.entities.requests.RegisterRequest;
import com.springboot.bankingapp.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connect")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @PostMapping(path = "/c/reg")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(path = "/c/auth")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticateRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
