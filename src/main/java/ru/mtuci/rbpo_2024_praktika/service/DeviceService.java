package ru.mtuci.rbpo_2024_praktika.service;

import com.mtuci.ukhanov.models.ApplicationUser;
import com.mtuci.ukhanov.models.Device;
import com.mtuci.ukhanov.requests.DataDeviceRequest;

import java.util.List;
import java.util.Optional;

public interface DeviceService {
    Device registerOrUpdateDevice(String nameDevice, String macDevice, ApplicationUser user);
    Optional<Device> findDeviceByInfo(String name, String mac_address, ApplicationUser user);
    Optional<Device> findDeviceById(Long id);

    // save
    Device save(DataDeviceRequest deviceRequest);

    // read
    List<Device> getAll();

    // update
    Device update(DataDeviceRequest deviceRequest);

    // delete
    void delete(Long id);
}