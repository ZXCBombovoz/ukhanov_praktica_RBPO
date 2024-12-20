package com.mtuci.ukhanov.exceptions.categories;

import com.mtuci.ukhanov.exceptions.DeviceLicenseException;

public class DeviceLicenseNotFoundException extends DeviceLicenseException {
    public DeviceLicenseNotFoundException(String msg) { super(msg); }
    public DeviceLicenseNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
