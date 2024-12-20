package ru.mtuci.ukhanov.exceptions;

public abstract class DeviceLicenseException extends RuntimeException {
    public DeviceLicenseException(String msg) {
        super(msg);
    }
    public DeviceLicenseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}