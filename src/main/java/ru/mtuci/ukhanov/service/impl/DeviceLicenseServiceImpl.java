package ru.mtuci.ukhanov.service.impl;

import ru.mtuci.ukhanov.exceptions.categories.DeviceLicenseNotFoundException;
import ru.mtuci.ukhanov.exceptions.categories.DeviceNotFoundException;
import ru.mtuci.ukhanov.exceptions.categories.License.LicenseNotFoundException;
import ru.mtuci.ukhanov.model.DeviceLicense;
import ru.mtuci.ukhanov.repository.DeviceLicenseRepository;
import ru.mtuci.ukhanov.repository.LicenseRepository;
import ru.mtuci.ukhanov.request.DataDeviceLicenseRequest;
import ru.mtuci.ukhanov.service.DeviceLicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceLicenseServiceImpl implements DeviceLicenseService {
    private final DeviceServiceImpl deviceService;
    private final LicenseRepository licenseRepository;
    private final DeviceLicenseRepository deviceLicenseRepository;

    private DeviceLicense edit(DeviceLicense deviceLicense, DataDeviceLicenseRequest request) {
       deviceLicense.setDevice(deviceService.findDeviceById(request.getDevice_id()).orElseThrow(
               () -> new DeviceNotFoundException("Устройство не найдено")
       ));
       deviceLicense.setLicense(licenseRepository.findById(request.getLicense_id()).orElseThrow(
               () -> new LicenseNotFoundException("Лицензия не найдена")
       ));
       deviceLicense.setActivation_date(request.getActivation_date());
       return deviceLicense;
    }

    @Override
    public DeviceLicense saveDeviceLicense(DeviceLicense deviceLicense) {
        return deviceLicenseRepository.save(deviceLicense);
    }

    @Override
    public DeviceLicense save(DataDeviceLicenseRequest request) {
        return deviceLicenseRepository.save(edit(new DeviceLicense(), request));
    }

    @Override
    public List<DeviceLicense> getAll() {
        return deviceLicenseRepository.findAll();
    }

    @Override
    public DeviceLicense update(DataDeviceLicenseRequest request) {
        DeviceLicense deviceLicense = deviceLicenseRepository.findById(request.getDevice_id()).orElseThrow(
                () -> new DeviceLicenseNotFoundException("Устройство-лицензия не найдено")
        );
        return deviceLicenseRepository.save(edit(deviceLicense, request));
    }

    @Override
    public void delete(Long id) {
        deviceLicenseRepository.deleteById(id);
    }
}