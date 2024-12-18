package ru.mtuci.rbpo_2024_praktika.requests;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String login, email, password;
}
