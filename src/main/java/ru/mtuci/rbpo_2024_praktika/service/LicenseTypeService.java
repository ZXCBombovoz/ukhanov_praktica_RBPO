package ru.mtuci.rbpo_2024_praktika.service;

import com.mtuci.ukhanov.models.LicenseType;
import com.mtuci.ukhanov.requests.DataLicenseTypeRequest;

import java.util.List;
import java.util.Optional;

public interface LicenseTypeService {
    Optional<LicenseType> getLicenseTypeById(Long id);

    // save
    LicenseType save(DataLicenseTypeRequest request);

    // read
    List<LicenseType> getAll();

    // update
    LicenseType update(DataLicenseTypeRequest request);

    // delete
    void delete(Long id);
}
