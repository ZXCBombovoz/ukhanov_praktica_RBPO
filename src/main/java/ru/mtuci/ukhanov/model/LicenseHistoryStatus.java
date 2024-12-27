package ru.mtuci.ukhanov.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LicenseHistoryStatus {
    CREATE("создана"),
    MODIFICATION("изменена"),
    ACTIVATE("активирована"),
    ERROR("ошибка"),
    EXPIRED("истекла");

    private final String status;
}
