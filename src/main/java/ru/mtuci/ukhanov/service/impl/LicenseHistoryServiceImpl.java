package ru.mtuci.ukhanov.service.impl;

import ru.mtuci.ukhanov.exceptions.categories.License.LicenseNotFoundException;
import ru.mtuci.ukhanov.exceptions.categories.LicenseHistoryNotFoundException;
import ru.mtuci.ukhanov.exceptions.categories.UserNotFoundException;
import ru.mtuci.ukhanov.model.ApplicationUser;
import ru.mtuci.ukhanov.model.License;
import ru.mtuci.ukhanov.model.LicenseHistory;
import ru.mtuci.ukhanov.repository.LicenseHistoryRepository;
import ru.mtuci.ukhanov.repository.LicenseRepository;
import ru.mtuci.ukhanov.request.DataLicenseHistoryRequest;
import ru.mtuci.ukhanov.service.LicenseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenseHistoryServiceImpl implements LicenseHistoryService {
    private final LicenseHistoryRepository licenseHistoryRepository;
    private final LicenseRepository licenseRepository;
    private final UserServiceImpl userService;

    @Override
    public boolean recordLicenseChange(License license, ApplicationUser owner, String status, String description) {
        LicenseHistory licenseHistory = new LicenseHistory();
        licenseHistory.setLicense(license);
        licenseHistory.setChange_date(new Date(System.currentTimeMillis()));
        licenseHistory.setUser(owner);
        licenseHistory.setStatus(status);
        licenseHistory.setDescription(description);

        licenseHistoryRepository.save(licenseHistory);
        return true;
    }

    @Override
    public Optional<LicenseHistory> findById(Long id) {
        return licenseHistoryRepository.findById(id);
    }

    private LicenseHistory edit(LicenseHistory licenseHistory, DataLicenseHistoryRequest request) {
        licenseHistory.setLicense(licenseRepository.findById(request.getLicense_id()).orElseThrow(
                () -> new LicenseNotFoundException("Лицензия не найдена")
        ));
        licenseHistory.setUser(userService.getUserById(request.getUser_id()).orElseThrow(
                () -> new UserNotFoundException("Пользователь не найден")
        ));
        licenseHistory.setStatus(request.getStatus());
        licenseHistory.setDescription(request.getDescription());
        licenseHistory.setChange_date(new Date(System.currentTimeMillis()));
        return licenseHistory;
    }

    @Override
    public LicenseHistory save(DataLicenseHistoryRequest request) {
        return licenseHistoryRepository.save(edit(new LicenseHistory(), request));
    }

    @Override
    public List<LicenseHistory> getAll() {
        return licenseHistoryRepository.findAll();
    }

    @Override
    public LicenseHistory update(DataLicenseHistoryRequest request) {
        LicenseHistory licenseHistory = licenseHistoryRepository.findById(request.getLicense_id()).orElseThrow(
                () -> new LicenseHistoryNotFoundException("История лицензии не найдена")
        );
        return licenseHistoryRepository.save(edit(licenseHistory, request));
    }

    @Override
    public void delete(Long id) {
        licenseHistoryRepository.deleteById(id);
    }
}
