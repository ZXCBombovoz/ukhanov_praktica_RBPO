package com.mtuci.ukhanov.service.impl;

import com.mtuci.ukhanov.exceptions.categories.License.LicenseErrorActivationException;
import com.mtuci.ukhanov.exceptions.categories.License.LicenseNotFoundException;
import com.mtuci.ukhanov.exceptions.categories.LicenseTypeNotFoundException;
import com.mtuci.ukhanov.exceptions.categories.ProductNotFoundException;
import com.mtuci.ukhanov.exceptions.categories.UserNotFoundException;
import com.mtuci.ukhanov.models.*;
import com.mtuci.ukhanov.repositories.LicenseRepository;
import com.mtuci.ukhanov.requests.DataLicenseRequest;
import com.mtuci.ukhanov.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {
    private final LicenseRepository licenseRepository;
    private final ProductServiceImpl productService;
    private final UserServiceImpl userService;
    private final LicenseTypeServiceImpl licenseTypeService;
    private final LicenseHistoryServiceImpl licenseHistoryService;
    private final DeviceLicenseServiceImpl deviceLicenseService;

    @Override
    public License createLicense(
            Long productId, Long ownerId, Long licenseTypeId,
            Integer device_count, Long duration) {
        Product product = productService.getProductById(productId).orElseThrow(
                () -> new ProductNotFoundException("Продукт не найден"));
        ApplicationUser owner = userService.getUserById(ownerId).orElseThrow(
                () -> new UserNotFoundException("Пользователь не найден"));
        LicenseType licenseType = licenseTypeService.getLicenseTypeById(licenseTypeId).orElseThrow(
                () -> new LicenseTypeNotFoundException("Тип лицензии не найден"));

        // Создание новой лицензии
        License license = new License();

        // Установка свойств лицензии
        license.setDevice_count(device_count);
        license.setBlocked(false);
        license.setUser(null);

        // Активация только при введении кода
        license.setFirst_activation_date(null);

        // Генерация активационного кода
        String code = generateCodeLicense(productId, ownerId, licenseTypeId, device_count);
        license.setCode(code);

        // Установка владельца, продукта, типа
        license.setProduct(product);
        license.setOwner(owner);
        license.setLicenseType(licenseType);

        // Проверка параметров
        if (duration == 0)
            duration = licenseType.getDefault_duration();
        license.setDuration(duration);

        // Расчёт даты окончания
        Date endDate = new Date(System.currentTimeMillis() + duration*1000);
        license.setEnding_date(endDate);

        StringBuilder description = new StringBuilder();
        description.append(String.format("Тип лицензии: %s\n", licenseType.getName()));
        description.append(String.format("Продукт: %s\n", product.getName()));
        description.append(String.format("Владелец: %s\n", owner.getLogin()));
        description.append(String.format("Кол-во устройств: %d\n", device_count));

        Format formatter = new SimpleDateFormat("dd.MM.yyyy");
        description.append(String.format("Действует до: %s\n", formatter.format(endDate)));

        license.setDescription(description.toString());
        license = licenseRepository.save(license);

        licenseHistoryService.recordLicenseChange(license, owner, LicenseHistoryStatus.CREATE.name(), description.toString());
        return license;
    }


    @Override
    public Ticket activateLicense(String activationCode, Device device, ApplicationUser user) {
        License license = licenseRepository.findByCode(activationCode).orElseThrow(
                () -> new LicenseNotFoundException("Лицензция не найдена")
        );

        if (!validateLicense(license, device, user))
        {
            licenseHistoryService.recordLicenseChange(license, user, LicenseHistoryStatus.ERROR.name(), "Активация лицензии невозможна");
            throw new LicenseErrorActivationException("Активация невозможна");
        }

        if (license.getUser() == null)
            license.setUser(user);
        updateLicense(license);

        createDeviceLicense(license, device);

        licenseHistoryService.recordLicenseChange(license, user, LicenseHistoryStatus.ACTIVATE.name(), "Лицензия успешно активирована");
        return generateTicket(license, device, "Лицензия активировна");
    }

    @Override
    public boolean validateLicense(License license, Device device, ApplicationUser user) {
        /*
        ======================================================================
            лицензция заблокирована или
            лицензия принадлежит другому пользователю или
            лицензия на данное устройство уже активирована или
            достигнуто максимальное кол-во устройств для активации или
            срок действия лицензии истёк
            то лицензию невозожно активировать
        ======================================================================
        */

        return !license.isBlocked() &&
                (license.getUser() == null || license.getUser().getId().equals(user.getId())) &&
                license.getDeviceLicenses().stream().noneMatch(deviceLicense ->
                        deviceLicense.getDevice().getId().equals(device.getId()) &&
                        deviceLicense.getLicense().getId().equals(license.getId())
                        ) &&
                license.getDeviceLicenses().size() < license.getDevice_count() &&
                new Date(System.currentTimeMillis()).before(license.getEnding_date());
    }

    @Override
    public void createDeviceLicense(License license, Device device) {
        DeviceLicense deviceLicense = new DeviceLicense();
        deviceLicense.setDevice(device);
        deviceLicense.setLicense(license);
        deviceLicense.setActivation_date(license.getFirst_activation_date());
        deviceLicenseService.saveDeviceLicense(deviceLicense);
    }

    @Override
    public void updateLicense(License license) {
        if (license.getFirst_activation_date() == null)
            license.setFirst_activation_date(new Date(System.currentTimeMillis()));

        Format formatter = new SimpleDateFormat("dd.MM.yyyy");
        license.setDescription(license.getDescription() + String.format("Пользователь: %s\n"+
                                "Впервые активирована: %s\nАктивированных устройств: %d",
                        license.getUser().getLogin(),
                        formatter.format(license.getFirst_activation_date()),
                        license.getDeviceLicenses().size() + 1
                        )
        );

        license = licenseRepository.save(license);
        licenseHistoryService.recordLicenseChange(license, license.getUser(), LicenseHistoryStatus.MODIFICATION.name(), license.getDescription());
    }

    @Override
    public List<License> getActiveLicensesForDevice(Device device, ApplicationUser user) {
        /*
        ======================================================================
        лицензия активна если:
            - лицензция не заблокирована
            - лицензиию имеет текущий пользователь
            - срок действия лицензии не истёк
        ======================================================================
        */

        return device.getDeviceLicenses().stream()
                .map(DeviceLicense::getLicense)
                .filter(license ->
                        license.getUser().getId().equals(user.getId()) &&
                                !license.isBlocked() &&
                                license.getEnding_date().after(new Date(System.currentTimeMillis()))
                ).toList();
    }

    private License edit(License license, DataLicenseRequest request) {
        license.setLicenseType(licenseTypeService.getLicenseTypeById(request.getType_id()).orElseThrow(
                () -> new LicenseTypeNotFoundException("Тип лицензии не найден")
        ));
        license.setProduct(productService.getProductById(request.getProduct_id()).orElseThrow(
                () -> new ProductNotFoundException("Продукт не найден")
        ));
        license.setUser(userService.getUserById(request.getUser_id()).orElseThrow(
                () -> new UsernameNotFoundException("Пользователь не найден")
        ));
        license.setOwner(userService.getUserById(request.getOwner_id()).orElseThrow(
                () -> new UsernameNotFoundException("Владелец не найден")
        ));
        license.setFirst_activation_date(request.getFirst_activation_date());
        license.setEnding_date(request.getEnding_date());
        license.setBlocked(request.isBlocked());
        license.setDevice_count(request.getDevice_count());
        license.setDuration(request.getDuration());
        license.setDescription(request.getDescription());
        return license;
    }

    @Override
    public License save(DataLicenseRequest request) {
        return licenseRepository.save(edit(new License(), request));
    }

    @Override
    public List<License> getAll() {
        return licenseRepository.findAll();
    }

    @Override
    public License update(DataLicenseRequest request) {
        License license = licenseRepository.findById(request.getId()).orElseThrow(
                () -> new LicenseNotFoundException("Лицензия не найдена")
        );
        return licenseRepository.save(edit(license, request));
    }

    @Override
    public void delete(Long id) {
        licenseRepository.deleteById(id);
    }

//    @Override
//    public Optional<License> findLicenseById(Long id) {
//        return licenseRepository.findById(id);
//    }

    @Override
    public Ticket generateTicket(License license, Device device, String description) {
        Ticket ticket = new Ticket();
        ticket.setNowDate(new Date(System.currentTimeMillis()));
        ticket.setActivationDate(license.getFirst_activation_date());
        ticket.setExpirationDate(license.getEnding_date());
        ticket.setExpiration(license.getDuration());
        ticket.setUserID(license.getUser().getId());
        ticket.setDeviceID(device.getId());
        ticket.setBlockedLicence(license.isBlocked());
        ticket.setDescription(description);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String ds = bCryptPasswordEncoder.encode(
                ticket.getNowDate().toString() + ticket.getActivationDate().toString() +
                        ticket.getExpirationDate().toString() + ticket.getExpiration().toString() +
                        ticket.getUserID().toString() + ticket.getDeviceID().toString()
        );
        ticket.setDigitalSignature(ds);
        return ticket;
    }

    @Override
    public List<Ticket> licenseRenewal(String activationCode, ApplicationUser user) {
        // Проверка ключа лицензии
        License license = licenseRepository.findByCode(activationCode).orElseThrow(
                () -> new LicenseNotFoundException("Ключ лицензии недействителен")
        );

        List<Ticket> tickets = license.getDeviceLicenses().stream()
                .map(deviceLicense -> generateTicket(license, deviceLicense.getDevice(), "")).toList();

        // Проверка возможности продления
        if (
                license.isBlocked() ||
                license.getEnding_date().before(new Date(System.currentTimeMillis()))  ||
                (license.getUser() != null && !license.getUser().getId().equals(user.getId()))
        )
        {
            tickets.forEach(ticket -> {
                ticket.setDescription("Невозможно продлить лицензию");
                licenseHistoryService.recordLicenseChange(license, user, LicenseHistoryStatus.ERROR.name(), ticket.getDescription());
            });
            return tickets;
        }

        // Продление на год
        license.setDuration(license.getLicenseType().getDefault_duration());
        license.setEnding_date(new Date(System.currentTimeMillis() + license.getDuration()*1000));

        tickets.forEach(ticket -> {
            ticket.setDescription("Лицензия успешно продлена");
            licenseHistoryService.recordLicenseChange(license, user, LicenseHistoryStatus.MODIFICATION.name(), ticket.getDescription());
        });
        return tickets;
    }

    private String generateCodeLicense(Long productId, Long ownerId, Long licenseTypeId, Integer device_count) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(productId.toString() + ownerId.toString() + licenseTypeId.toString() + device_count.toString());
    }
}