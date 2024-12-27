package ru.mtuci.ukhanov.repository;

import ru.mtuci.ukhanov.model.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseTypeRepository extends JpaRepository<LicenseType, Long> {
}
