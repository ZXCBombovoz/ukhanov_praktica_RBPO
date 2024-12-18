package ru.mtuci.rbpo_2024_praktika.repositories;

import com.mtuci.ukhanov.models.ApplicationUser;
import com.mtuci.ukhanov.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByNameAndMacAddressAndUser(String name, String mac_address, ApplicationUser user);
    Optional<Device> findByMacAddress(String mac_address);
}
