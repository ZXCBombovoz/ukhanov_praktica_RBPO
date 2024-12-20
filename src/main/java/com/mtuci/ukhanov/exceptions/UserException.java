package com.mtuci.ukhanov.exceptions;

public abstract class UserException extends RuntimeException {
    public UserException(String msg) {
        super(msg);
    }
    public UserException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
