package ru.mtuci.rbpo_2024_praktika.service.impl;

import com.mtuci.ukhanov.exceptions.categories.License.LicenseNotFoundException;
import com.mtuci.ukhanov.exceptions.categories.LicenseHistoryNotFoundException;
import com.mtuci.ukhanov.exceptions.categories.UserNotFoundException;
import com.mtuci.ukhanov.models.ApplicationUser;
import com.mtuci.ukhanov.models.License;
import com.mtuci.ukhanov.models.LicenseHistory;
import com.mtuci.ukhanov.repositories.LicenseHistoryRepository;
import com.mtuci.ukhanov.repositories.LicenseRepository;
import com.mtuci.ukhanov.requests.DataLicenseHistoryRequest;
import com.mtuci.ukhanov.service.LicenseHistoryService;
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
