package ru.mtuci.rbpo_2024_praktika.requests;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String login, password;
}
