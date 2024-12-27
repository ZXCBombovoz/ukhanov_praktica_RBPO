package ru.mtuci.ukhanov.repository;

import ru.mtuci.ukhanov.model.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LicenseRepository extends JpaRepository<License, Long> {
    Optional<License> findByCode(String licenseCode);
    List<License> findAllByCode(String licenseCode);
}
