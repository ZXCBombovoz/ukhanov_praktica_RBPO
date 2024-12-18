package ru.mtuci.rbpo_2024_praktika.repositories;

import com.mtuci.ukhanov.models.LicenseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseHistoryRepository extends JpaRepository<LicenseHistory, Long> {
}
