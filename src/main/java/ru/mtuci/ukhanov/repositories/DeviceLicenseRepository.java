package ru.mtuci.ukhanov.repositories;

import ru.mtuci.ukhanov.models.Device;
import ru.mtuci.ukhanov.models.DeviceLicense;
import ru.mtuci.ukhanov.models.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceLicenseRepository extends JpaRepository<DeviceLicense, Long> {
    Optional<DeviceLicense> findByDeviceAndLicense(Device device, License license);
}
