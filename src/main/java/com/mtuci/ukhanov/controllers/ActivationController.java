package com.mtuci.ukhanov.controllers;

import com.mtuci.ukhanov.configuration.JwtTokenProvider;
import com.mtuci.ukhanov.exceptions.categories.UserNotFoundException;
import com.mtuci.ukhanov.models.ApplicationUser;
import com.mtuci.ukhanov.models.Device;
import com.mtuci.ukhanov.models.Ticket;
import com.mtuci.ukhanov.requests.DeviceRequest;
import com.mtuci.ukhanov.service.impl.DeviceServiceImpl;
import com.mtuci.ukhanov.service.impl.LicenseServiceImpl;
import com.mtuci.ukhanov.service.impl.UserServiceImpl;
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
