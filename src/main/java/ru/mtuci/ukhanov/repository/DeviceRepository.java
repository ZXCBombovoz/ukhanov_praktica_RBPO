package ru.mtuci.ukhanov.repository;

import ru.mtuci.ukhanov.model.ApplicationUser;
import ru.mtuci.ukhanov.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByNameAndMacAddressAndUser(String name, String mac_address, ApplicationUser user);
    Optional<Device> findByMacAddress(String mac_address);
}
