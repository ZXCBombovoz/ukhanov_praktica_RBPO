package ru.mtuci.ukhanov.request;

import lombok.Data;

@Data
public class LicenseUpdateRequest {
    private String password, codeActivation, durationAdd;
}
