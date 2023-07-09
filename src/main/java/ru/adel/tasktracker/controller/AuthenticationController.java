package ru.adel.tasktracker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.adel.tasktracker.dto.AuthenticationRequest;
import ru.adel.tasktracker.dto.AuthenticationResponse;
import ru.adel.tasktracker.dto.UserRequest;
import ru.adel.tasktracker.security.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse register(@Valid @RequestBody UserRequest registerRequest) {
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticate(authenticationRequest);
    }
}
