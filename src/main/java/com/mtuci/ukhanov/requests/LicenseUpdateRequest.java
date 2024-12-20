package com.mtuci.ukhanov.requests;

import lombok.Data;

@Data
public class LicenseUpdateRequest {
    private String password, codeActivation;
}
