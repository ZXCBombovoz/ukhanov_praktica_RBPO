package com.mtuci.ukhanov.repositories;

import com.mtuci.ukhanov.models.LicenseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseHistoryRepository extends JpaRepository<LicenseHistory, Long> {
}
