package ru.mtuci.ukhanov.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataUserRequest {
    private Long id;
    private String login, password, email, role;
}
