package ru.mtuci.ukhanov.service;

import ru.mtuci.ukhanov.models.ApplicationUser;
import ru.mtuci.ukhanov.models.License;
import ru.mtuci.ukhanov.models.LicenseHistory;
import ru.mtuci.ukhanov.requests.DataLicenseHistoryRequest;

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
