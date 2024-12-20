package ru.mtuci.ukhanov.repositories;

import ru.mtuci.ukhanov.models.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseTypeRepository extends JpaRepository<LicenseType, Long> {
}
