package ru.mtuci.ukhanov.requests;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String login, password;
}
