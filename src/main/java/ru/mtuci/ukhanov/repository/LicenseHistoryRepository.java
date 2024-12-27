package ru.mtuci.ukhanov.repository;

import ru.mtuci.ukhanov.model.LicenseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseHistoryRepository extends JpaRepository<LicenseHistory, Long> {
}
