package com.mtuci.ukhanov.controllers;

import com.mtuci.ukhanov.models.ApplicationUser;
import com.mtuci.ukhanov.requests.RegistrationRequest;
import com.mtuci.ukhanov.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserServiceImpl userDetailsService;

    @PostMapping
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest registrationRequest) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setLogin(registrationRequest.getLogin());
        applicationUser.setEmail(registrationRequest.getEmail());

        if (!userDetailsService.saveUser(applicationUser, registrationRequest.getPassword()))
            return ResponseEntity.badRequest().body("Пользователь уже существует!");

        return ResponseEntity.ok("Регистрация прошла успешно!");
    }
}
