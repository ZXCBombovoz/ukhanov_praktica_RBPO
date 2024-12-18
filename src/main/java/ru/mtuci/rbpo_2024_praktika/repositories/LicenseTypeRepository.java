package ru.mtuci.rbpo_2024_praktika.repositories;

import com.mtuci.ukhanov.models.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseTypeRepository extends JpaRepository<LicenseType, Long> {
}
