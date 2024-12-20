package com.mtuci.ukhanov.exceptions.categories;

import com.mtuci.ukhanov.exceptions.AuthenticationException;

public class AuthenticationErrorException extends AuthenticationException {
    public AuthenticationErrorException(String msg) {
        super(msg);
    }
    public AuthenticationErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
