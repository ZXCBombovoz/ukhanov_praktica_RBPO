package com.mtuci.ukhanov.exceptions.categories;

import com.mtuci.ukhanov.exceptions.LicenseTypeException;

public class LicenseTypeNotFoundException extends LicenseTypeException {
    public LicenseTypeNotFoundException(String msg) {
        super(msg);
    }
    public LicenseTypeNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
