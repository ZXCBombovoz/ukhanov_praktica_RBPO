package ru.mtuci.ukhanov.exceptions.categories;

import ru.mtuci.ukhanov.exceptions.LicenseHistoryException;

public class LicenseHistoryNotFoundException extends LicenseHistoryException {
    public LicenseHistoryNotFoundException(String msg) {
        super(msg);
    }
    public LicenseHistoryNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
