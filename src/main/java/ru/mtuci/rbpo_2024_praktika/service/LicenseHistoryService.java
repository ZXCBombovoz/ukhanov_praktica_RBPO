package ru.mtuci.rbpo_2024_praktika.service;

import com.mtuci.ukhanov.models.ApplicationUser;
import com.mtuci.ukhanov.models.License;
import com.mtuci.ukhanov.models.LicenseHistory;
import com.mtuci.ukhanov.requests.DataLicenseHistoryRequest;

import java.util.List;
import java.util.Optional;

public interface LicenseHistoryService {
    boolean recordLicenseChange(
            License license, ApplicationUser owner,
            String status, String description);
    Optional<LicenseHistory> findById(Long id);

    // save
    LicenseHistory save(DataLicenseHistoryRequest request);

    // read
    List<LicenseHistory> getAll();

    // update
    LicenseHistory update(DataLicenseHistoryRequest request);

    // delete
    void delete(Long id);
}
