package com.mtuci.ukhanov.controllers;

import com.mtuci.ukhanov.configuration.JwtTokenProvider;
import com.mtuci.ukhanov.exceptions.categories.DeviceNotFoundException;
import com.mtuci.ukhanov.exceptions.categories.UserNotFoundException;
import com.mtuci.ukhanov.models.ApplicationUser;
import com.mtuci.ukhanov.models.Device;
import com.mtuci.ukhanov.models.License;
import com.mtuci.ukhanov.models.Ticket;
import com.mtuci.ukhanov.requests.DeviceInfoRequest;
import com.mtuci.ukhanov.service.impl.DeviceServiceImpl;
import com.mtuci.ukhanov.service.impl.LicenseServiceImpl;
import com.mtuci.ukhanov.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/licenseInfo")
@RequiredArgsConstructor
public class LicenseInfoController {
    private final UserServiceImpl userService;
    private final DeviceServiceImpl deviceService;
    private final JwtTokenProvider jwtTokenProvider;
    private final LicenseServiceImpl licenseService;

    @PostMapping
    public ResponseEntity<?> getLicenseInfo(@RequestHeader("Authorization") String auth, @RequestBody DeviceInfoRequest deviceInfoRequest) {
        try {
            // Получить аутентифицированного пользователя
            String login = jwtTokenProvider.getUsername(auth.split(" ")[1]);
            ApplicationUser user = userService.getUserByLogin(login).orElseThrow(
                    () -> new UserNotFoundException("User not found")
            );

            // Получить устройство
            Device device = deviceService.findDeviceByInfo(deviceInfoRequest.getName(), deviceInfoRequest.getMacAddress(), user).orElseThrow(
                    () -> new DeviceNotFoundException("Устройство не найдено")
            );

            List<License> licenses = licenseService.getActiveLicensesForDevice(device, user);
            List<Ticket> tickets = licenses.stream().map(license -> licenseService.generateTicket(license, device, "Информация о лицензии на текущее устройство")).toList();

            return ResponseEntity.ok(tickets);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(String.format("Ошибка(%s)", e.getMessage()));
        }
    }
}