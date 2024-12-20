package com.mtuci.ukhanov.controllers;

import com.mtuci.ukhanov.models.License;
import com.mtuci.ukhanov.requests.DataLicenseRequest;
import com.mtuci.ukhanov.requests.LicenseCreateRequest;
import com.mtuci.ukhanov.service.impl.LicenseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/licenseCreate")
@RequiredArgsConstructor
public class LicenseCreateController {
    private final LicenseServiceImpl licenseService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createLicense(@RequestBody LicenseCreateRequest licenseCreateRequest) {
        try {
            License license = licenseService.createLicense(
                    licenseCreateRequest.getProductId(),
                    licenseCreateRequest.getOwnerId(),
                    licenseCreateRequest.getLicenseTypeId(),
                    licenseCreateRequest.getDevice_count(),
                    licenseCreateRequest.getDuration());

            return ResponseEntity.ok(new DataLicenseRequest(
                    license.getId(),
                    license.getLicenseType().getId(),
                    license.getProduct().getId(),
                    null,
                    license.getOwner().getId(),
                    license.getFirst_activation_date(),
                    license.getEnding_date(),
                    license.isBlocked(),
                    license.getDevice_count(),
                    license.getDuration(),
                    license.getCode(),
                    license.getDescription()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(String.format("Ошибка(%s)", e.getMessage()));
        }
    }
}
