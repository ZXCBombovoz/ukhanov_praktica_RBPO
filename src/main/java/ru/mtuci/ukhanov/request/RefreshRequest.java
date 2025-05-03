package ru.mtuci.ukhanov.request;

import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
    private String deviceId;
}