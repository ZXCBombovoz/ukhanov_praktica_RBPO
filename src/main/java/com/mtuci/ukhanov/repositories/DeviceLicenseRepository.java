package com.mtuci.ukhanov.repositories;

import com.mtuci.ukhanov.models.Device;
import com.mtuci.ukhanov.models.DeviceLicense;
import com.mtuci.ukhanov.models.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceLicenseRepository extends JpaRepository<DeviceLicense, Long> {
    Optional<DeviceLicense> findByDeviceAndLicense(Device device, License license);
}
