package ru.mtuci.ukhanov.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataDeviceRequest {
    private Long id, user_id;
    private String name, macAddress;
}
