package ru.mtuci.ukhanov.service;

import ru.mtuci.ukhanov.model.LicenseType;
import ru.mtuci.ukhanov.request.DataLicenseTypeRequest;

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
