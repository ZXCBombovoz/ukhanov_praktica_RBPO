package ru.mtuci.ukhanov.controllers;

import ru.mtuci.ukhanov.configuration.JwtTokenProvider;
import ru.mtuci.ukhanov.exceptions.categories.UserNotFoundException;
import ru.mtuci.ukhanov.models.ApplicationUser;
import ru.mtuci.ukhanov.models.Device;
import ru.mtuci.ukhanov.models.Ticket;
import ru.mtuci.ukhanov.requests.DeviceRequest;
import ru.mtuci.ukhanov.service.impl.DeviceServiceImpl;
import ru.mtuci.ukhanov.service.impl.LicenseServiceImpl;
import ru.mtuci.ukhanov.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activation")
@RequiredArgsConstructor
public class ActivationController {
    private final UserServiceImpl userService;
    private final DeviceServiceImpl deviceService;
    private final JwtTokenProvider jwtTokenProvider;
    private final LicenseServiceImpl licenseService;

    @PostMapping
    public ResponseEntity<?> activateLicense(@RequestHeader("Authorization") String auth, @RequestBody DeviceRequest deviceRequest) {
        try {
            // Получить аутентифицированного пользователя
            String login = jwtTokenProvider.getUsername(auth.split(" ")[1]);
            ApplicationUser user = userService.getUserByLogin(login).orElseThrow(
                    () -> new UserNotFoundException("Пользователь не найден")
            );

            // Получить устройство
            Device device = deviceService.registerOrUpdateDevice(deviceRequest.getName(), deviceRequest.getMacAddress(), user);

            Ticket ticket = licenseService.activateLicense(deviceRequest.getActivationCode(), device, user);

            return ResponseEntity.ok(ticket);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(String.format("Ошибка(%s)", e.getMessage()));
        }
    }
}
