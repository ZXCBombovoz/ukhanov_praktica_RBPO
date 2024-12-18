package ru.mtuci.rbpo_2024_praktika.requests;

import lombok.Data;

@Data
public class DeviceInfoRequest {
    private String name, macAddress;
}
