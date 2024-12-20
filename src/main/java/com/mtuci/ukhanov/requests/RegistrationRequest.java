package com.mtuci.ukhanov.requests;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String login, email, password;
}
