package ru.mtuci.ukhanov.exceptions.categories;

import ru.mtuci.ukhanov.exceptions.UserException;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
    public UserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
