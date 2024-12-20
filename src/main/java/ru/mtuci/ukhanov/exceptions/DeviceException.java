package ru.mtuci.ukhanov.exceptions;

public abstract class DeviceException extends RuntimeException {
    public DeviceException(String msg) {
        super(msg);
    }
    public DeviceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
