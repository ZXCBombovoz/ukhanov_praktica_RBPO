package ru.mtuci.ukhanov.controller;

import ru.mtuci.ukhanov.configuration.JwtTokenProvider;
import ru.mtuci.ukhanov.exceptions.categories.UserNotFoundException;
import ru.mtuci.ukhanov.exceptions.categories.License.LicenseNotFoundException;
import ru.mtuci.ukhanov.model.ApplicationUser;
import ru.mtuci.ukhanov.model.Device;
import ru.mtuci.ukhanov.model.License;
import ru.mtuci.ukhanov.model.Ticket;
import ru.mtuci.ukhanov.request.DeviceRequest;
import ru.mtuci.ukhanov.service.impl.DeviceServiceImpl;
import ru.mtuci.ukhanov.service.impl.LicenseServiceImpl;
import ru.mtuci.ukhanov.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/licenseInfo")
@RequiredArgsConstructor
public class LicenseInfoController {
    private final UserServiceImpl userService;
    private final DeviceServiceImpl deviceService;
    private final JwtTokenProvider jwtTokenProvider;
    private final LicenseServiceImpl licenseService;

    @PostMapping
    public ResponseEntity<?> getLicenseInfo(
        @RequestHeader("Authorization") String auth, 
        @RequestBody DeviceRequest deviceRequest
    ) {
        try {
            // Получить аутентифицированного пользователя
            String login = jwtTokenProvider.getUsername(auth.split(" ")[1]);
            ApplicationUser user = userService.getUserByLogin(login).orElseThrow(
                    () -> new UserNotFoundException("User not found")
            );

            // Получить устройство
            Device device = deviceService.registerOrUpdateDevice(
                deviceRequest.getName(), 
                deviceRequest.getMacAddress(), 
                user
            );

            // Найти лицензию по коду активации
            License license = licenseService.getLicenseByActivationCode(deviceRequest.getActivationCode());

            // Проверить, что пользователь имеет доступ к лицензии
            if (!license.getUser().getId().equals(user.getId())) {
                throw new LicenseNotFoundException("У вас нет доступа к этой лицензии");
            }
            Ticket ticket = licenseService.generateTicket(
                license, 
                device, 
                "Информация о лицензии по коду активации"
            );

            return ResponseEntity.ok(ticket);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(String.format("Ошибка(%s)", e.getMessage()));
        }
    }
}