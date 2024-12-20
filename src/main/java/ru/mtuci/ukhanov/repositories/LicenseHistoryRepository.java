package ru.mtuci.ukhanov.repositories;

import ru.mtuci.ukhanov.models.LicenseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseHistoryRepository extends JpaRepository<LicenseHistory, Long> {
}
