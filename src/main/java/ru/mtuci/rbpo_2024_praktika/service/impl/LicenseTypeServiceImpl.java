package ru.mtuci.rbpo_2024_praktika.service.impl;

import com.mtuci.ukhanov.exceptions.categories.LicenseTypeNotFoundException;
import com.mtuci.ukhanov.models.LicenseType;
import com.mtuci.ukhanov.repositories.LicenseTypeRepository;
import com.mtuci.ukhanov.requests.DataLicenseTypeRequest;
import com.mtuci.ukhanov.service.LicenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenseTypeServiceImpl implements LicenseTypeService {
    private final LicenseTypeRepository licenseTypeRepository;

    @Override
    public Optional<LicenseType> getLicenseTypeById(Long id) {
        return licenseTypeRepository.findById(id);
    }

    private LicenseType edit(LicenseType licenseType, DataLicenseTypeRequest request) {
        licenseType.setName(request.getName());
        licenseType.setDescription(request.getDescription());
        licenseType.setDefault_duration(request.getDefault_duration());
        return licenseType;
    }

    @Override
    public LicenseType save(DataLicenseTypeRequest request) {
        return licenseTypeRepository.save(edit(new LicenseType(), request));
    }

    @Override
    public List<LicenseType> getAll() {
        return licenseTypeRepository.findAll();
    }

    @Override
    public LicenseType update(DataLicenseTypeRequest request) {
        LicenseType licenseType = licenseTypeRepository.findById(request.getId()).orElseThrow(
                () -> new LicenseTypeNotFoundException("Тип лицензии не найден")
        );
        return licenseTypeRepository.save(edit(licenseType, request));
    }

    @Override
    public void delete(Long id) {
        licenseTypeRepository.deleteById(id);
    }
}
