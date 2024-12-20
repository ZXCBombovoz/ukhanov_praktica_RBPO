package com.mtuci.ukhanov.requests;

import lombok.Data;

@Data
public class DeviceInfoRequest {
    private String name, macAddress;
}
