package ru.mtuci.ukhanov.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String login, password;
}
