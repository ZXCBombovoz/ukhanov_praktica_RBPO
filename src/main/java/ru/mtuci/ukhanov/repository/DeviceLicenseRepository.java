package ru.mtuci.ukhanov.repository;

import ru.mtuci.ukhanov.model.Device;
import ru.mtuci.ukhanov.model.DeviceLicense;
import ru.mtuci.ukhanov.model.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceLicenseRepository extends JpaRepository<DeviceLicense, Long> {
    Optional<DeviceLicense> findByDeviceAndLicense(Device device, License license);
}
