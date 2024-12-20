package com.mtuci.ukhanov.exceptions.categories.License;

import com.mtuci.ukhanov.exceptions.LicenseException;

public class LicenseErrorActivationException extends LicenseException {
    public LicenseErrorActivationException(String msg) {
        super(msg);
    }
    public LicenseErrorActivationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
