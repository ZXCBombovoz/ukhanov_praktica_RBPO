package ru.mtuci.ukhanov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.ukhanov.configuration.JwtTokenProvider;
import ru.mtuci.ukhanov.exceptions.categories.AuthenticationErrorException;
import ru.mtuci.ukhanov.exceptions.categories.UserNotFoundException;
import ru.mtuci.ukhanov.model.ApplicationUser;
import ru.mtuci.ukhanov.model.Ticket;
import ru.mtuci.ukhanov.request.LicenseUpdateRequest;
import ru.mtuci.ukhanov.service.impl.AuthenticationServiceImpl;
import ru.mtuci.ukhanov.service.impl.LicenseServiceImpl;
import ru.mtuci.ukhanov.service.impl.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/licenseUpdate")
@RequiredArgsConstructor
public class LicenseUpdateController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;
    private final AuthenticationServiceImpl authenticationService;
    private final LicenseServiceImpl licenseService;

    @PostMapping
    public ResponseEntity<?> licenseUpdate(@RequestHeader("Authorization") String auth, @RequestBody LicenseUpdateRequest licenseUpdateRequest) {
        try {
            // Получить аутентифицированного пользователя
            String login = jwtTokenProvider.getUsername(auth.split(" ")[1]);
            ApplicationUser user = userService.getUserByLogin(login).orElseThrow(
                    () -> new UserNotFoundException("Пользователь не найден")
            );

            // Аунтентификация пользователя
            if (!authenticationService.authenticate(user, licenseUpdateRequest.getPassword()))
                throw new AuthenticationErrorException("Аутентификация не удалась");

            // запрос на продление
            List<Ticket> tickets = licenseService.licenseRenewal(licenseUpdateRequest.getCodeActivation(), user);

            return ResponseEntity.ok(tickets);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(String.format("Ошибка(%s)", e.getMessage()));
        }
    }
}