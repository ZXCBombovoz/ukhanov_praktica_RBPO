package ru.mtuci.ukhanov.exceptions.categories;

import ru.mtuci.ukhanov.exceptions.DeviceException;

public class DeviceNotFoundException extends DeviceException {
    public DeviceNotFoundException(String msg) { super(msg); }
    public DeviceNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
