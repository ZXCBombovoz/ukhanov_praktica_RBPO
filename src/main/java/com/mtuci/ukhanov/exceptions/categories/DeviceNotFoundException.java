package com.mtuci.ukhanov.exceptions.categories;

import com.mtuci.ukhanov.exceptions.DeviceException;

public class DeviceNotFoundException extends DeviceException {
    public DeviceNotFoundException(String msg) { super(msg); }
    public DeviceNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
