package ru.mtuci.ukhanov.request;

import lombok.Data;

@Data
public class DeviceRequest {
    private String activationCode, name, macAddress;
}
