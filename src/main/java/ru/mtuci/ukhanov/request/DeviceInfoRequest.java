package ru.mtuci.ukhanov.request;

import lombok.Data;

@Data
public class DeviceInfoRequest {
    private String name, macAddress;
}
