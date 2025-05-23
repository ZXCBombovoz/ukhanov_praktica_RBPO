package ru.mtuci.ukhanov.controller;

import ru.mtuci.ukhanov.model.DeviceLicense;
import ru.mtuci.ukhanov.request.DataDeviceLicenseRequest;
import ru.mtuci.ukhanov.service.impl.DeviceLicenseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage/deviceLicense")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DeviceLicenseController {
    private final DeviceLicenseServiceImpl deviceLicenseService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody DataDeviceLicenseRequest request) {
        try {
            DeviceLicense device = deviceLicenseService.save(request);
            request.setId(device.getId());
            return ResponseEntity.ok(request);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<DeviceLicense> devices = deviceLicenseService.getAll();
            List<DataDeviceLicenseRequest> dataDevices = devices.stream().map(
                    device -> new DataDeviceLicenseRequest(
                            device.getId(),
                            device.getDevice().getId(),
                            device.getLicense().getId(),
                            device.getActivation_date()
                    )
            ).toList();
            return ResponseEntity.ok(dataDevices);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody DataDeviceLicenseRequest request) {
        try {
            deviceLicenseService.update(request);
            return ResponseEntity.ok(request);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        try {
            deviceLicenseService.delete(id);
            return ResponseEntity.ok("Удалено Устрйство-Лицензия");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
